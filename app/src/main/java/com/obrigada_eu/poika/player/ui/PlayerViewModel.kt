package com.obrigada_eu.poika.player.ui

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrigada_eu.poika.player.ui.formatters.SpeedStringFormatter
import com.obrigada_eu.poika.player.ui.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.ui.mappers.toTitleString
import com.obrigada_eu.poika.player.data.infra.audio.ChangeSpeedDirection
import com.obrigada_eu.poika.player.data.infra.audio.RewindDirection
import com.obrigada_eu.poika.shared.domain.audio.AudioService
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.shared.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.shared.domain.time.toPlaybackPositionMs
import com.obrigada_eu.poika.shared.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.shared.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.shared.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.player.ui.mappers.toUi
import com.obrigada_eu.poika.player.ui.mappers.toSpeedString
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
class   PlayerViewModel @Inject constructor(
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


    val songTitleText: StateFlow<String?> = playerSessionReader.currentSongFlow { metaData ->
        metaData.toTitleString()
    }

    val volumeMap: StateFlow<Map<String, Float>> = playerSessionReader.volumeLevelsFlow()

    val progressStateUi: StateFlow<ProgressStateUi> = progressProvider.mapState { state ->
        state.toUi(TimeStringFormatter)
    }

    val isPlaying: StateFlow<Boolean> = playerSessionReader.isPlayingFlow()

    val currentSpeedUi: StateFlow<String> = playerSessionReader.mapSpeed { speed ->
        speed.toSpeedString(SpeedStringFormatter)
    }

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

    fun showMessage(message: Spannable, shortDuration: Boolean = true) {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowToast(message, shortDuration)) }
    }

    fun showMessage(message: String, shortDuration: Boolean = true) {
        showMessage(SpannableString(message), shortDuration)
    }

    fun handleZipImport(uriString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = importZipUseCase(uriString)
            if (result != null) {

                val text = "Song ${result.toTitleString()} is available in your list"
                val spannable = SpannableString(text).apply {
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        5,
                        text.length - 26,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
                showMessage(spannable, false)
            } else {
                showMessage("Error importing a song")
            }
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
    fun rewind(direction: RewindDirection) = audioService.rewind(
        if (direction == RewindDirection.BACK) - 5000L else + 5000L
    )

    fun changeSpeed(direction: ChangeSpeedDirection) = audioService.changeSpeed(
        if (direction == ChangeSpeedDirection.BACK) - 0.1f else + 0.1f
    )

    fun setVolume(part: String, volume: Float) {
        audioService.setVolume(part, volume)
    }


    fun setSongProgress(sliderPosition: Float) {
        val newPosition = sliderPosition.toPlaybackPositionMs()
        audioService.seekTo(newPosition)
    }

    private fun showListDialog(list: List<SongMetaData>, mode: UiEvent.Mode) {
        viewModelScope.launch { _uiEvent.send(UiEvent.ShowSongDialog(list, mode)) }
    }

    companion object {
        private const val TAG = "PlayerViewModel"
    }
}