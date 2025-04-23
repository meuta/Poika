package com.obrigada_eu.poika.player

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.ui.player.ProgressStateFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AudioController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val progressState: ProgressStateFlow
) {

    private var playerIsReady = false

    private val players: List<ExoPlayer> = List(3) { ExoPlayer.Builder(context).build() }

    private val handler = Handler(Looper.getMainLooper())

    private val updateProgress: Runnable = object : Runnable {
        override fun run() {
            updateProgress(current = minOf(getCurrentPosition(), getDuration()))
            handler.postDelayed(this, 100)
        }
    }

    private var currentSongMetaData: SongMetaData? = null

    private fun runProgress(run: Boolean) {
        if (run) updateProgress.run() else handler.removeCallbacks(updateProgress)
    }

    private fun updateProgress(current: Long = 0) {
        progressState.update(progressState.value().copy(current = current))
    }

    private fun updateProgressFinish() {
        progressState.update(progressState.value().copy(isFinished = true))
    }

    private fun updateDuration(duration: Long?) {
        duration?.let { progressState.update(progressState.value().copy(duration = it)) }
    }

    fun loadTracks(songMetaData: SongMetaData) {

        stop()
        currentSongMetaData = songMetaData

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
                        stop()
                        updateProgressFinish()
                    }
                }
            })
        }
    }

    fun getCurrentSong(): SongMetaData? = currentSongMetaData

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

    fun release() {
        players.forEach { it.release() }
    }

    fun getPlayers(): List<ExoPlayer> = players

    fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in players.indices) {
            players[trackIndex].volume = volume.coerceIn(0f, 1f)
        }
    }


    fun seekToAll(positionMs: Long) {
        players.forEach { it.seekTo(positionMs) }
        updateProgress(current = positionMs)
    }

    fun getCurrentPosition(): Long {
        return players.firstOrNull()?.currentPosition ?: 0L
    }

    fun getDuration(): Long {
        return players.firstOrNull()?.duration ?: 0L
    }


    companion object {
        private const val TAG = "AudioController"
    }
}