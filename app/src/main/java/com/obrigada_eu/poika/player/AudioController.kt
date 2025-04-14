package com.obrigada_eu.poika.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioController @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    fun play(mediaUri: Uri) {
        val mediaItem = MediaItem.fromUri(mediaUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        player.release()
    }

    fun isPlaying(): Boolean = player.isPlaying

    fun getPlayer(): ExoPlayer = player
}