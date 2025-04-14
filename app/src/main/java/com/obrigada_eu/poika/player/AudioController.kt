package com.obrigada_eu.poika.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioController @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val players: List<ExoPlayer> = List(3) {
        ExoPlayer.Builder(context).build()
    }

    fun loadTracks(uri1: String, uri2: String, uri3: String) {
        val uris = listOf(uri1, uri2, uri3)
        for (i in uris.indices) {
            players[i].apply {
                setMediaItem(MediaItem.fromUri(uris[i]))
                prepare()
            }
        }
    }

    fun play() {
        players.forEach {
            if (it.playbackState == Player.STATE_IDLE) { it.prepare() }
            it.playWhenReady = true
        }
    }

    fun pause() {
        players.forEach { it.playWhenReady = false }
    }

    fun stop() {
        players.forEach {
            it.stop()
            it.seekTo(0)
        }
    }

    fun release() {
        players.forEach { it.release() }
    }

    fun getPlayers(): List<ExoPlayer> = players
}