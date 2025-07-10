package com.obrigada_eu.poika.player.data.infra.audio

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.obrigada_eu.poika.common.formatters.toTitleString
import com.obrigada_eu.poika.player.domain.contracts.AudioService
import com.obrigada_eu.poika.player.domain.progress.ProgressStateUpdater
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AudioController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val progressTracker: ProgressStateUpdater,
    private val playerSessionWriter: PlayerSessionWriter,
) : AudioService {

    private val players: List<ExoPlayer> = List(3) { ExoPlayer.Builder(context).build() }

    private var playerIsReady = false

    private val handler = Handler(Looper.getMainLooper())

    private val progressTicker: Runnable = object : Runnable {
        override fun run() {
            updateProgressTracker(currentPosition = minOf(getCurrentPosition(), getDuration()))
            handler.postDelayed(this, 100)
        }
    }


    override fun loadTracks(songMetaData: SongMetaData) {

        stop()
        playerSessionWriter.setCurrentSongTitle(songMetaData.toTitleString())

        val base = File(context.filesDir, "songs/${songMetaData.folderName}")
        val (uri1, uri2, uri3) = listOf("Soprano", "Alto", "Minus").map { part ->
            songMetaData.tracks.find { it.name == part }?.file?.let { File(base, it).toUri() }
        }

        val uris = listOf(uri1, uri2, uri3)
        for (i in uris.indices) {
            players[i].apply {
                uris[i]?.let {
                    setMediaItem(MediaItem.fromUri(it))
                    prepare()
                }
            }
        }

        players.firstOrNull()?.let { player ->
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        val durationMs = player.duration
                        updateDuration(durationMs)
                        playerIsReady = true
                    }
                    if (state == Player.STATE_ENDED) {
                        stop()
                    }
                }
            })
        }
    }


    override fun play() {
        if (playerIsReady) {
            players.forEach {
                if (it.playbackState == Player.STATE_IDLE) {
                    it.prepare()
                }
                it.playWhenReady = true
            }
            runProgressTicker(true)
        }
    }

    override fun pause() {
        players.forEach { it.playWhenReady = false }
        runProgressTicker(false)
    }

    override fun stop() {
        players.forEach {
            it.stop()
            it.playWhenReady = false
        }
        seekTo(0)
        runProgressTicker(false)
    }

    override fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in players.indices) {
            players[trackIndex].volume = volume
            playerSessionWriter.setVolume(trackIndex, volume)
        }
    }

    override fun seekTo(positionMs: Long) {
        players.forEach { it.seekTo(positionMs) }
        updateProgressTracker(currentPosition = positionMs)
    }


    private fun getCurrentPosition(): Long {
        return players.firstOrNull()?.currentPosition ?: 0L
    }

    private fun getDuration(): Long {
        return players.firstOrNull()?.duration ?: 0L
    }


    private fun runProgressTicker(run: Boolean) {
        if (run) progressTicker.run() else handler.removeCallbacks(progressTicker)
    }

    private fun updateProgressTracker(currentPosition: Long) {
        progressTracker.update(progressTracker.value().copy(currentPosition = currentPosition))
    }


    private fun updateDuration(duration: Long) {
        progressTracker.update(progressTracker.value().copy(duration = duration))
    }
}