package com.obrigada_eu.poika.shared.presentation.player

import com.obrigada_eu.poika.shared.domain.audio.AudioService
import com.obrigada_eu.poika.shared.domain.audio.ChangeSpeedDirection
import com.obrigada_eu.poika.shared.domain.audio.RewindDirection
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.shared.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.shared.domain.time.toPlaybackPositionMs
import com.obrigada_eu.poika.shared.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.shared.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.shared.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.shared.presentation.player.formatters.SpeedStringFormatter
import com.obrigada_eu.poika.shared.presentation.player.formatters.TimeStringFormatter
import com.obrigada_eu.poika.shared.presentation.player.mappers.toSpeedString
import com.obrigada_eu.poika.shared.presentation.player.mappers.toTitleString
import com.obrigada_eu.poika.shared.presentation.player.mappers.toUi
import com.obrigada_eu.poika.shared.presentation.player.model.ProgressStateUi
import com.obrigada_eu.poika.shared.presentation.player.model.UiEvent
import com.obrigada_eu.poika.shared.presentation.player.model.UiTextPart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val audioService: AudioService,
    private val importZipUseCase: ImportZipUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val loadSongUseCase: LoadSongUseCase,
    private val deleteSongUseCase: DeleteSongUseCase,
    progressProvider: ProgressStateProvider,
    playerSessionReader: PlayerSessionReader
) {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

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

    fun dispose() {
        scope.cancel()
    }

    fun showChooseDialog() = showListDialog(UiEvent.Mode.CHOOSE)
    fun showDeleteDialog() = showListDialog(UiEvent.Mode.DELETE)

    fun showHelpDialog() {
        scope.launch { _uiEvent.send(UiEvent.ShowHelpDialog) }
    }

    private fun showListDialog(mode: UiEvent.Mode) {
        scope.launch(Dispatchers.IO) {
            val songs = getAllSongsUseCase()
            if (songs.isEmpty()) {
                showMessage("The song list is empty.")
            } else {
                showListDialog(songs, mode)
            }
        }
    }

    fun showMessage(message: List<UiTextPart>, shortDuration: Boolean = true) {
        scope.launch { _uiEvent.send(UiEvent.ShowToast(message, shortDuration)) }
    }

    fun showMessage(message: String, shortDuration: Boolean = true) {
        showMessage(listOf(UiTextPart(message)), shortDuration)
    }

    fun handleZipImport(uriString: String) {
        scope.launch(Dispatchers.IO) {
            val result = importZipUseCase(uriString)
            if (result != null) {

                val songTitle = result.toTitleString()

                val message = listOf(
                    UiTextPart("Song "),
                    UiTextPart(songTitle, bold = true),
                    UiTextPart(" is available in your list")
                )
                showMessage(message, false)
            } else {
                showMessage("Error importing a song")
            }
        }
    }


    fun loadSong(songMetaData: SongMetaData) {
        loadSongUseCase(songMetaData)
    }

    fun deleteSongs(songs: List<SongMetaData>) {
        scope.launch {
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
        scope.launch { _uiEvent.send(UiEvent.ShowSongDialog(list, mode)) }
    }

    companion object {
        private const val TAG = "PlayerViewModel"
    }
}