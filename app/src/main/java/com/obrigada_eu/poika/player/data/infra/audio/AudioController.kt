package com.obrigada_eu.poika.player.data.infra.audio

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.obrigada_eu.poika.common.formatters.toTitleString
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AudioController @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val progressTracker: ProgressTracker,
    private val playerSession: PlayerSessionWriter,
) {

    private val players: List<ExoPlayer> = List(3) { ExoPlayer.Builder(context).build() }

    private var playerIsReady = false

    private val handler = Handler(Looper.getMainLooper())

    private val progressUpdater: Runnable = object : Runnable {
        override fun run() {
            updateProgress(currentPosition = minOf(getCurrentPosition(), getDuration()))
            handler.postDelayed(this, 100)
        }
    }


    fun loadTracks(songMetaData: SongMetaData) {

        stop()
        playerSession.setCurrentSongTitle(songMetaData.toTitleString())

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
        players.firstOrNull()?.apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_READY) {
                        val durationMs = duration
                        updateDuration(durationMs)
                        playerIsReady = true
                    }
                    if (state == Player.STATE_ENDED) {
                        this@AudioController.stop()
                        updateProgressFinish()
                    }
                }
            })
        }
    }


    fun play() {
        if (playerIsReady) {
            players.forEach {
                if (it.playbackState == Player.STATE_IDLE) {
                    it.prepare()
                }
                it.playWhenReady = true
            }
            runProgress(true)
        }
    }

    fun pause() {
        players.forEach { it.playWhenReady = false }
        runProgress(false)
    }

    fun stop() {
        players.forEach {
            it.stop()
            it.playWhenReady = false
        }
        seekToAll(0)
        runProgress(false)
    }

    fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in players.indices) {
            volume.coerceIn(0f, 1f).let {
                players[trackIndex].volume = it
                playerSession.setVolume(trackIndex, it)
            }
        }
    }

    fun seekToAll(positionMs: Long) {
        players.forEach { it.seekTo(positionMs) }
        updateProgress(currentPosition = positionMs)
    }


    private fun getCurrentPosition(): Long {
        return players.firstOrNull()?.currentPosition ?: 0L
    }

    private fun getDuration(): Long {
        return players.firstOrNull()?.duration ?: 0L
    }


    private fun runProgress(run: Boolean) {
        if (run) progressUpdater.run() else handler.removeCallbacks(progressUpdater)
    }

    private fun updateProgress(currentPosition: Long = 0) {
        progressTracker.update(progressTracker.value().copy(currentPosition = currentPosition))
    }


    private fun updateDuration(duration: Long?) {
        duration?.let { progressTracker.update(progressTracker.value().copy(duration = it)) }
    }

    private fun updateProgressFinish() {
        progressTracker.update(progressTracker.value().copy(isFinished = true))
    }
}