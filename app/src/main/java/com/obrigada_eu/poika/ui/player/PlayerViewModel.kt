package com.obrigada_eu.poika.ui.player

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.player.AudioController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.SharedFlow


@HiltViewModel
class PlayerViewModel @Inject constructor(
    application: Application,
    private val audioController: AudioController,
    progressState: ProgressStateFlow,
    progressUiMapper: ProgressMapper,
    private val importZipUseCase: ImportZipUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase
) : AndroidViewModel(application) {


    private val _songs = MutableStateFlow<List<SongMetaData>>(emptyList())
    val songs: StateFlow<List<SongMetaData>> = _songs


    private val _toastMessage = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val toastMessage: SharedFlow<String> = _toastMessage

    fun showMessage(message: String) {
        viewModelScope.launch { _toastMessage.emit(message) }
    }

    fun handleZipImport(uri: Uri) {
        if (importZipUseCase(uri) == null) showMessage("Error importing a song")
    }

    fun loadSongsList() {
        val songs = getAllSongsUseCase()
        _songs.value = getAllSongsUseCase()
        if (songs.isEmpty()) showMessage("The song list is empty.")
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