package com.obrigada_eu.poika.ui.player

import androidx.lifecycle.ViewModel
import com.obrigada_eu.poika.player.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val audioController: AudioController
) : ViewModel() {

    fun loadTracks(uri1: String, uri2: String, uri3: String) = audioController.loadTracks(uri1, uri2, uri3)

    fun play() = audioController.play()
    fun pause() = audioController.pause()
    fun stop() = audioController.stop()

    fun setVolume(trackIndex: Int, progress: Int) {
        val volume = progress / 100f
        audioController.setVolume(trackIndex, volume)
    }

    override fun onCleared() {
        super.onCleared()
        audioController.release()
    }
}