package com.obrigada_eu.poika.player

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.ui.player.ProgressStateFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class AudioController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val progressState: ProgressStateFlow
) {

    private var playerIsReady = false

    private val players: List<ExoPlayer> = List(3) { ExoPlayer.Builder(context).build() }
//    private val videoPlayers: List<ExoPlayer> = List(1) { ExoPlayer.Builder(context).build() }

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

    @OptIn(UnstableApi::class)
    fun loadTracks(songMetaData: SongMetaData) {

        stop()
        currentSongMetaData = songMetaData

        val base = File(context.filesDir, "songs/${songMetaData.folderName}")
        val (uri1, uri2, uri3) = listOf("Soprano", "Alto", "Minus").map { part ->
            songMetaData.tracks.find { it.name == part }?.file?.let { File(base, it).toUri() }
        }
        Log.d(TAG, "loadTracks: uri1 = $uri1")

        val (videoUri1, videoUri2) = listOf("TextVideoSoprano", "TextVideoAlto").map { part ->
            songMetaData.video.find { it.name == part }?.file?.let { File(base, it).toUri() }
        }
        Log.d(TAG, "loadTracks: videoUri1 = $videoUri1")

//        val uris = listOf(uri1, uri2, uri3)
//        val uris = listOf(videoUri1, uri2, uri3)
        val uris = listOf(videoUri1, videoUri2, uri3)
        val videoUris = listOf(videoUri1, videoUri2, null)
//        val videoUris = listOf(videoUri1, null, null)
//        val videoUris = listOf(videoUri1)

////        val videoSource1 = videoUri1?.let {
////            ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
////            .createMediaSource(MediaItem.fromUri(videoUri1))
////        }
//        val dataSourceFactory = DefaultDataSource.Factory(context)
//
//        val videoSources = videoUris.map { uri -> uri?.let {
//            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
//        } }
//
//        val audioSources = uris.map { uri -> uri?.let {
//            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
//        } }
//
////        val mergedSource = videoSources.mapIndexed { index, value ->
////            value?.let { audioSources[index]?.let { MergingMediaSource(value, it) } }
////        }
//        val mergedSource = audioSources.mapIndexed { index, value ->
//            value?.let { videoSources[index]?.let {
//                Log.d(TAG, "loadTracks: video = $it")
//                Log.d(TAG, "loadTracks: audio = $value")
//                MergingMediaSource(it, value)
//            }
//            }
//        }

        for (i in uris.indices) {
            players[i].apply {
                uris[i]?.let {
//                    if (mergedSource[i] != null) {
//                        setMediaSource(mergedSource[i]!!, false)
//                    } else {
                        setMediaItem(MediaItem.fromUri(it))
//                    }
                    prepare()
                }
            }
        }

//        for (i in videoUris.indices) {
//            videoPlayers[i].apply {
//                videoUris[i]?.let {
//                    setMediaItem(MediaItem.fromUri(it))
//                    prepare()
//                }
//            }
//        }

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

    fun getCurrentSong(): SongMetaData? = currentSongMetaData

    fun play() {
        if (playerIsReady) {
            players.forEach {
//                CoroutineScope(Dispatchers.Main).launch {
                    if (it.playbackState == Player.STATE_IDLE) {
                        it.prepare()
                    }
                    it.playWhenReady = true
//                }

            }
//            videoPlayers.forEach {
//                if (it.playbackState == Player.STATE_IDLE) {
//                    it.prepare()
//                }
//                it.playWhenReady = true
//            }
            runProgress(true)
        }
    }

    fun pause() {
        players.forEach { it.playWhenReady = false }
//        videoPlayers.forEach { it.playWhenReady = false }
        runProgress(false)
    }

    fun stop() {
        players.forEach {
            it.stop()
            it.playWhenReady = false
        }
//        videoPlayers.forEach {
//            it.stop()
//            it.playWhenReady = false
//        }
        seekToAll(0)
        runProgress(false)
    }

    fun release() {
        players.forEach { it.release() }
    }

    fun getPlayers(): List<ExoPlayer> = players
//    fun getVideoPlayers(): List<ExoPlayer> = videoPlayers

    fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in players.indices) {
            players[trackIndex].volume = volume.coerceIn(0f, 1f)
        }
    }


    fun seekToAll(positionMs: Long) {
        if (isPlaying()) {
            pause()
            players.forEach {
                it.seekTo(positionMs)
            }
            play()
        } else {
            players.forEach {
//            CoroutineScope(Dispatchers.Main).launch {
                it.seekTo(positionMs)
//            }
            }
        }
//        videoPlayers.forEach { it.seekTo(positionMs) }
        updateProgress(current = positionMs)
    }

    fun getCurrentPosition(): Long {
        Log.d(TAG, "getCurrentPosition: \n")
        players.forEachIndexed { i, player ->
            Log.d(TAG, "getCurrentPosition: $i - ${player.currentPosition}\n")
        }
        return players.firstOrNull()?.currentPosition ?: 0L
    }

    fun getDuration(): Long {
        return players.firstOrNull()?.duration ?: 0L
    }

    fun isPlaying(): Boolean {
        return players.firstOrNull()?.isPlaying == true
    }


    companion object {
        private const val TAG = "AudioController"
    }
}