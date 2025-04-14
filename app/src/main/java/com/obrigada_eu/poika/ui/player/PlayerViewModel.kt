package com.obrigada_eu.poika.ui.player

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.obrigada_eu.poika.player.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val audioController: AudioController
) : ViewModel() {


    val player = audioController.getPlayer()

    fun play(uri: Uri) {
        audioController.play(uri)
    }

    fun pause() {
        audioController.pause()
    }

    fun stop() {
        audioController.stop()
    }

    override fun onCleared() {
        super.onCleared()
        audioController.release()
    }
}