package com.obrigada_eu.poika.ui.player

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.player.AudioController
import com.obrigada_eu.poika.ui.SongMetaDataMapper
import com.obrigada_eu.poika.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.receiveAsFlow


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val audioController: AudioController,
    progressState: ProgressStateFlow,
    progressUiMapper: ProgressMapper,
    private val songMetaDataMapper: SongMetaDataMapper,
    private val importZipUseCase: ImportZipUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val loadSongUseCase: LoadSongUseCase,
    private val deleteSongUseCase: DeleteSongUseCase
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun showChooseDialog() = showListDialog(UiEvent.Mode.CHOOSE)
    fun showDeleteDialog() = showListDialog(UiEvent.Mode.DELETE)

    fun showListDialog(mode: UiEvent.Mode) {
        viewModelScope.launch(Dispatchers.IO) {
            val songs = getAllSongsUseCase()
            if (songs.isEmpty()) {
                showMessage("The song list is empty.")
            } else {
                showListDialog(songs, mode)
            }
        }
    }

    fun showListDialog(list: List<SongMetaData>, mode: UiEvent.Mode) {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowSongDialog(list, mode)) }
    }

    private val _songTitleText = MutableStateFlow<String?>(null)
    val songTitleText: StateFlow<String?> = _songTitleText

    fun setSongTitleText(title: String) {
        _songTitleText.value = title
    }

    fun showMessage(message: String) {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowToast(message)) }
    }


    fun handleZipImport(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = importZipUseCase(uri)
            showMessage(result?.let { "New song is available" } ?: "Error importing a song")
        }
    }

    val progressFlow: StateFlow<ProgressStateUi> = progressState.map(progressUiMapper)

    fun loadSong(songMetaData: SongMetaData) {
        loadSongUseCase(songMetaData)
        setSongTitleText(songMetaDataMapper.mapToSongTitle(songMetaData))
    }

    fun refreshUiState() {
        audioController.getCurrentSong()?.let {
            setSongTitleText(songMetaDataMapper.mapToSongTitle(it))
        }
    }

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

    fun deleteSongs(songs: List<SongMetaData>) {
        viewModelScope.launch {
            val success = songs.map { deleteSongUseCase(it) }.contains(false).not()
            showMessage(if (success) "Songs deleted" else "Deletion error")
        }
    }

}

