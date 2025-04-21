package com.obrigada_eu.poika.ui.player

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.poika.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.player.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.SharedFlow


@HiltViewModel
class PlayerViewModel @Inject constructor(
    application: Application,
    private val audioController: AudioController,
    progressState: ProgressStateFlow,
    progressUiMapper: ProgressMapper
) : AndroidViewModel(application) {

    private var _singleMessage = MutableSharedFlow<String>()
    val singleMessage: SharedFlow<String> = _singleMessage

    suspend fun showMessage(message: String) {
        _singleMessage.emit(message)
    }

    private val importZipUseCase = ImportZipUseCase(application)

    fun handleZipImport(uri: Uri) {
        viewModelScope.launch {
            if (importZipUseCase(uri) == null) showMessage("Error importing a song")
        }
    }

    val progressFlow: StateFlow<ProgressStateUi> = progressState.map(progressUiMapper)

    fun loadTracks(uri1: String, uri2: String, uri3: String) = audioController.loadTracks(uri1, uri2, uri3)

    fun play() = audioController.play()
    fun pause() = audioController.pause()
    fun stop() = audioController.stop()

    fun setVolume(trackIndex: Int, progress: Int) {
        val volume = progress / 100f
        audioController.setVolume(trackIndex, volume)
    }

    fun setSongProgress(progress: Int) {
        val newPosition = (progress * 1000).toLong()
        audioController.seekToAll(newPosition)
    }

    override fun onCleared() {
        super.onCleared()
        audioController.release()
    }

    companion object {
        private const val TAG = "PlayerViewModel"
    }
}