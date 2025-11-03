package com.obrigada_eu.poika.player.data.infra.audio

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.obrigada_eu.poika.common.formatters.toTrackInfoList
import com.obrigada_eu.poika.player.domain.contracts.AudioService
import com.obrigada_eu.poika.player.domain.progress.ProgressStateUpdater
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AudioController @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val progressTracker: ProgressStateUpdater,
    private val playerSessionWriter: PlayerSessionWriter,
) : AudioService {

    private var playersMap: Map<String, ExoPlayer> = mapOf()
    private var playerIsReady = false

    private val handler = Handler(Looper.getMainLooper())

    private val progressTicker: Runnable = object : Runnable {
        override fun run() {
            updateProgressTracker(currentPosition = minOf(getCurrentPosition(), getDuration()))
            handler.postDelayed(this, 100)
        }
    }

    private var isPlaying: Boolean = false

    override fun loadTracks(songMetaData: SongMetaData) {

        stop()

        val tracks = songMetaData.toTrackInfoList()

        // release extra players
        playersMap
            .filterKeys { part -> part !in tracks.map { it.name } }
            .values.forEach { player -> player.release() }

        // add players if need
        playersMap = tracks.map { it.name }.associateWith { part ->
            playersMap[part] ?: ExoPlayer.Builder(context).build().apply { volume = INITIAL_VOLUME }
        }

        // load tracks from files into players
        val base = File(context.filesDir, "songs/${songMetaData.folderName}")
        tracks.forEach { track ->
            val uri = File(base, track.file).toUri()
            playersMap[track.name]?.apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
            }
        }

        // add listener
        playersMap.values.firstOrNull()?.apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    when (state) {
                        Player.STATE_READY -> {
                            val durationMs = duration
                            updateDuration(durationMs)
                            playerIsReady = true
                        }
                        Player.STATE_ENDED -> {
                            this@AudioController.stop()
                        }
                        Player.STATE_BUFFERING -> {}
                        Player.STATE_IDLE -> {}
                    }
                }
            })
        }

        // set song title and parts in playerSession
        playerSessionWriter.setCurrentSong(songMetaData)
        playerSessionWriter.updateParts(playersMap.mapValues { (_, player) -> player.volume })
    }


    override fun togglePlayPause() {
        if (playerIsReady) {
            if (isPlaying) {
                pause()
                playerSessionWriter.setIsPlaying(false)
            } else {
                play()
                playerSessionWriter.setIsPlaying(true)
            }
            isPlaying = !isPlaying
        }
    }

    private fun play() {
        if (playerIsReady) {
            playersMap.values.forEach { player ->
                if (player.playbackState == Player.STATE_IDLE) {
                    player.prepare()
                }
                player.playWhenReady = true
            }
            runProgressTicker(true)
        }
    }

    private fun pause() {
        playersMap.values.forEach { player -> player.playWhenReady = false }
        runProgressTicker(false)
    }

    override fun stop() {
        playersMap.values.forEach { player ->
            player.stop()
            player.playWhenReady = false
        }
        seekTo(0)
        runProgressTicker(false)
        isPlaying = false
        playerSessionWriter.setIsPlaying(false)
    }

    override fun setVolume(part: String, volume: Float) {
        playersMap[part]?.volume = volume
        playerSessionWriter.setTrackVolume(part, volume)
    }

    override fun seekTo(positionMs: Long) {
        playersMap.values.forEach { player -> player.seekTo(positionMs) }
        updateProgressTracker(currentPosition = positionMs)
    }

    override fun rewind(durationMs: Long) {
        (getCurrentPosition() + durationMs)
            .coerceIn(0L..getDuration())
            .let { position ->
                playersMap.values.forEach { player -> player.seekTo(position) }
                updateProgressTracker(currentPosition = position)
            }
    }


    private fun getCurrentPosition(): Long {
        return playersMap.values.firstOrNull()?.currentPosition ?: 0L
    }

    private fun getDuration(): Long {
        return playersMap.values.firstOrNull()?.duration ?: 0L
    }


    private fun runProgressTicker(run: Boolean) {
        if (run) progressTicker.run() else handler.removeCallbacks(progressTicker)
    }

    private fun updateProgressTracker(currentPosition: Long) {
        progressTracker.update(progressTracker.currentState().copy(currentPosition = currentPosition))
    }

    private fun updateDuration(duration: Long) {
        progressTracker.update(progressTracker.currentState().copy(duration = duration))
    }

    companion object {

        private const val INITIAL_VOLUME = 1f
        private const val TAG = "AudioController"
    }
}

enum class RewindDirection {
    BACK, FORWARD
}