package com.obrigada_eu.poika.player.ui

import PlayerScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.ui.model.UiEvent
import com.obrigada_eu.poika.ui.utils.Toaster
import kotlinx.coroutines.flow.firstOrNull
import kotlin.collections.mapKeys
import kotlin.collections.mapOf

@Composable
fun PlayerScreenHost(
    playerViewModel: PlayerViewModel,
) {

    val context = LocalContext.current

    val emptySelectionText = stringResource(R.string.select_at_least_one)
    val stringZeroZero = stringResource(R.string._00_00)

    var showChooseSongDialog by remember { mutableStateOf(false) }
    var showDeleteSongDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }

    var songs by remember { mutableStateOf<List<SongMetaData>>(emptyList()) }
    var selectedSongs by remember { mutableStateOf<List<SongMetaData>>(emptyList()) }

    var menuExpanded by remember { mutableStateOf(false) }

    val songTitle by playerViewModel.songTitleText.collectAsState()


    var playbackSeekbarPosition by remember { mutableFloatStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }
    var currentPositionText by remember { mutableStateOf(stringZeroZero) }
    var trackDurationText by remember { mutableStateOf(stringZeroZero) }
    var playbackSeekbarMax by remember { mutableFloatStateOf(0f) }

    val volumeStates = remember { List(3) { mutableFloatStateOf(1f) } }

    LaunchedEffect(Unit) {
        playerViewModel.initialVolumeList.firstOrNull()?.let { list ->
            list.forEachIndexed { i, volume ->
                volumeStates[i].floatValue = volume
            }
        }
    }

    LaunchedEffect(Unit) {
        playerViewModel.progressStateUi.collect { progressState ->
            if (!isUserSeeking) {
                playbackSeekbarPosition = progressState.currentPositionSec
                currentPositionText = progressState.currentPositionString
            }
            trackDurationText = progressState.durationString
            playbackSeekbarMax = progressState.durationSec
        }
    }

    LaunchedEffect(Unit) {
        playerViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSongDialog -> {
                    songs = event.songs
                    when (event.mode) {
                        UiEvent.Mode.CHOOSE -> {
                            showChooseSongDialog = true
                        }
                        UiEvent.Mode.DELETE -> {
                            showDeleteSongDialog = true
                        }
                    }
                }
                is UiEvent.ShowToast -> {
                    if (event.message.isNotBlank()) {
                        Toaster.show(context, event.message)
                    }
                }
                is UiEvent.ShowHelpDialog -> {
                    showHelpDialog = true
                }
            }
        }
    }

    PlayerScreen(
        menuItems = mapOf(
            R.string.choose_song to playerViewModel::showChooseDialog,
            R.string.delete_song to playerViewModel::showDeleteDialog,
            R.string.help to playerViewModel::showHelpDialog
        )
            .mapKeys { stringResource(it.key) }
            .mapValues { (_, action) ->
                {
                    menuExpanded = false
                    action()
                }
            },
        menuExpanded = menuExpanded,
        menuIconOnClick = { menuExpanded = !menuExpanded },
        onDismissMenuRequest = { menuExpanded = false },
        songTitle = songTitle,
        currentPositionText = currentPositionText,
        trackDurationText = trackDurationText,
        playbackSeekbarPosition = playbackSeekbarPosition,
        playbackSeekbarMax = playbackSeekbarMax,
        onSeekChanged = { newValue ->
            isUserSeeking = true
            playbackSeekbarPosition = newValue
            currentPositionText = TimeStringFormatter.formatSecToString(newValue)
        },
        onSeekReleased = {
            playerViewModel.setSongProgress(playbackSeekbarPosition)
            isUserSeeking = false
        },
        playbackButtons = mapOf(
            R.string.play to playerViewModel::play,
            R.string.pause to playerViewModel::pause,
            R.string.stop to playerViewModel::stop
        ).mapKeys { stringResource(it.key) },
        volumeStates = volumeStates.map { it.floatValue },
        onSetVolume = { index, value ->
            playerViewModel.setVolume(index, value)
            volumeStates[index].floatValue = value
        },
        showChooseSongDialog = showChooseSongDialog,
        showDeleteSongDialog = showDeleteSongDialog,
        showDeleteConfirmationDialog = showDeleteConfirmationDialog,
        showHelpDialog = showHelpDialog,
        songs = songs,
        selectedSongs = selectedSongs,
        onLoadSong = playerViewModel::loadSong,
        onTryDeleteSongs = { selected ->
            selectedSongs = selected
            showDeleteSongDialog = false
            showDeleteConfirmationDialog = true
        },
        onConfirmDeleteSongs = {
            playerViewModel.deleteSongs(selectedSongs)
            showDeleteConfirmationDialog = false
            selectedSongs = emptyList()
        },
        onEmptySelection = { playerViewModel.showMessage(emptySelectionText) },
        onDismissChooseSongDialog = { showChooseSongDialog = false },
        onDismissDeleteSongDialog = { showDeleteSongDialog = false },
        onDismissDeleteConfirmationDialog = { showDeleteConfirmationDialog = false },
        onDismissHelpDialog = { showHelpDialog = false },
    )

}