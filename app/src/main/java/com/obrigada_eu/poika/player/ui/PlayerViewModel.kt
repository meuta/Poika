package com.obrigada_eu.poika.player.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.common.formatters.toTitleString
import com.obrigada_eu.poika.player.domain.contracts.AudioService
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.player.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.player.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.player.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.player.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.player.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.player.ui.mappers.toPlayerPosition
import com.obrigada_eu.poika.player.ui.mappers.toUi
import com.obrigada_eu.poika.player.ui.model.ProgressStateUi
import com.obrigada_eu.poika.player.ui.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val audioService: AudioService,
    private val importZipUseCase: ImportZipUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val loadSongUseCase: LoadSongUseCase,
    private val deleteSongUseCase: DeleteSongUseCase,
    progressProvider: ProgressStateProvider,
    playerSessionReader: PlayerSessionReader
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>(Channel.Factory.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()


    val songTitleText = playerSessionReader.currentSongFlow { metaData ->
        metaData.toTitleString()
    }

    val volumeList: StateFlow<List<Float>> = playerSessionReader.volumeLevelsFlow()

    val progressStateUi: StateFlow<ProgressStateUi> = progressProvider.mapState { state ->
        state.toUi(TimeStringFormatter)
    }

    val isPlaying = playerSessionReader.isPlayingFlow()

    fun showChooseDialog() = showListDialog(UiEvent.Mode.CHOOSE)
    fun showDeleteDialog() = showListDialog(UiEvent.Mode.DELETE)

    fun showHelpDialog() {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowHelpDialog) }
    }

    private fun showListDialog(mode: UiEvent.Mode) {
        viewModelScope.launch(Dispatchers.IO) {
            val songs = getAllSongsUseCase()
            if (songs.isEmpty()) {
                showMessage("The song list is empty.")
            } else {
                showListDialog(songs, mode)
            }
        }
    }

    fun showMessage(message: String) {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowToast(message)) }
    }

    private fun showListDialog(list: List<SongMetaData>, mode: UiEvent.Mode) {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowSongDialog(list, mode)) }
    }


    fun handleZipImport(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = importZipUseCase(uri)
            showMessage(result?.let { "New song is available" } ?: "Error importing a song")
        }
    }

    fun loadSong(songMetaData: SongMetaData) {
        loadSongUseCase(songMetaData)
    }

    fun deleteSongs(songs: List<SongMetaData>) {
        viewModelScope.launch {
            val success = songs.map { deleteSongUseCase(it) }.contains(false).not()
            showMessage(if (success) "Songs deleted" else "Deletion error")
        }
    }


    fun togglePlayPause() = audioService.togglePlayPause()
    fun stop() = audioService.stop()


    fun setVolume(trackIndex: Int, volume: Float) {
        audioService.setVolume(trackIndex, volume)
    }

    fun setSongProgress(sliderPosition: Float) {
        val newPosition = sliderPosition.toPlayerPosition()
        audioService.seekTo(newPosition)
    }
}