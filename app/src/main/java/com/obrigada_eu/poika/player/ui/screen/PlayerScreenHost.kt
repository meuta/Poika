package com.obrigada_eu.poika.player.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Remove
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
import coil3.request.ImageRequest
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.shared.presentation.player.formatters.TimeStringFormatter
import com.obrigada_eu.poika.shared.domain.audio.ChangeSpeedDirection
import com.obrigada_eu.poika.shared.domain.audio.RewindDirection
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.player.ui.components.SpeedControllerButtonType
import com.obrigada_eu.poika.player.ui.model.ImageButtonItem
import com.obrigada_eu.poika.player.ui.model.TriangleButtonItem
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.shared.presentation.player.model.UiEvent
import com.obrigada_eu.poika.ui.utils.Toaster
import kotlin.collections.mapKeys
import kotlin.collections.mapOf

@Composable
fun PlayerScreenHost(
    playerPresenter: PlayerPresenter,
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

    val songTitle by playerPresenter.songTitleText.collectAsState()


    var playbackSeekbarPosition by remember { mutableFloatStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }
    var currentPositionText by remember { mutableStateOf(stringZeroZero) }
    var trackDurationText by remember { mutableStateOf(stringZeroZero) }
    var playbackSeekbarMax by remember { mutableFloatStateOf(0f) }

    val currentSpeedText by playerPresenter.currentSpeedUi.collectAsState()

    val isPlaying by playerPresenter.isPlaying.collectAsState()

    val volumeStates by playerPresenter.volumeMap.collectAsState()


    LaunchedEffect(Unit) {
        playerPresenter.progressStateUi.collect { progressState ->
            if (!isUserSeeking) {
                playbackSeekbarPosition = progressState.currentPositionSec
                currentPositionText = progressState.currentPositionString
            }
            trackDurationText = progressState.durationString
            playbackSeekbarMax = progressState.durationSec
        }
    }

    LaunchedEffect(Unit) {
        playerPresenter.uiEvent.collect { event ->
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
                    if (event.message.isNotEmpty()) {
                        Toaster.show(context, event.message, event.shortDuration)
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
            R.string.choose_song to playerPresenter::showChooseDialog,
            R.string.delete_song to playerPresenter::showDeleteDialog,
            R.string.help to playerPresenter::showHelpDialog
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
        imageRequest = ImageRequest.Builder(context)
            .data("android.resource://${context.packageName}/${R.raw.logo_pink_512_512}")
            .build(),
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
            playerPresenter.setSongProgress(playbackSeekbarPosition)
            isUserSeeking = false
        },
        changeSpeedButtons = Pair(
            TriangleButtonItem(
                type = SpeedControllerButtonType.BACKWARD,
                label = stringResource(R.string.minus_speed),
                icon = Icons.Filled.Remove,
                onClick = { playerPresenter.changeSpeed(ChangeSpeedDirection.BACK) }),
            TriangleButtonItem(
                type = SpeedControllerButtonType.FORWARD,
                label = stringResource(R.string.plus_speed),
                icon = Icons.Filled.Add,
                onClick = { playerPresenter.changeSpeed(ChangeSpeedDirection.FORWARD) }
            ),
        ),
        currentSpeed = currentSpeedText,

        playbackButtons = listOf(
            ImageButtonItem(
                label = stringResource(R.string.minus_5_sec),
                icon = Icons.Filled.Replay5,
                weight = 2f,
                onClick = { playerPresenter.rewind(RewindDirection.BACK) }
            ),
            ImageButtonItem(
                label = stringResource(if (isPlaying) R.string.pause else R.string.play),
                icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                weight = 4f,
                onClick = playerPresenter::togglePlayPause
            ),
            ImageButtonItem(
                label = stringResource(R.string.stop),
                icon = Icons.Filled.Stop,
                weight = 3f,
                onClick = playerPresenter::stop
            ),
            ImageButtonItem(
                label = stringResource(R.string.plus_5_sec),
                icon = Icons.Filled.Forward5,
                weight = 2f,
                onClick = { playerPresenter.rewind(RewindDirection.FORWARD) }
            ),
        ),
        volumeStates = volumeStates,
        setVolume = playerPresenter::setVolume,
        showChooseSongDialog = showChooseSongDialog,
        showDeleteSongDialog = showDeleteSongDialog,
        showDeleteConfirmationDialog = showDeleteConfirmationDialog,
        showHelpDialog = showHelpDialog,
        songs = songs,
        selectedSongs = selectedSongs,
        onLoadSong = playerPresenter::loadSong,
        onTryDeleteSongs = { selected ->
            selectedSongs = selected
            showDeleteSongDialog = false
            showDeleteConfirmationDialog = true
        },
        onConfirmDeleteSongs = {
            playerPresenter.deleteSongs(selectedSongs)
            showDeleteConfirmationDialog = false
            selectedSongs = emptyList()
        },
        onEmptySelection = { playerPresenter.showMessage(emptySelectionText) },
        onDismissChooseSongDialog = { showChooseSongDialog = false },
        onDismissDeleteSongDialog = { showDeleteSongDialog = false },
        onDismissDeleteConfirmationDialog = { showDeleteConfirmationDialog = false },
        onDismissHelpDialog = { showHelpDialog = false },
    )
}

private const val TAG = "PlayerScreenHost"