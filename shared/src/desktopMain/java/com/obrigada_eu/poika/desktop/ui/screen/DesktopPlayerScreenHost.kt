package com.obrigada_eu.poika.desktop.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Forward5
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Replay5
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.obrigada_eu.poika.shared.domain.audio.ChangeSpeedDirection
import com.obrigada_eu.poika.shared.domain.audio.RewindDirection
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.platform
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.shared.presentation.player.formatters.TimeStringFormatter
import com.obrigada_eu.poika.shared.presentation.player.model.UiEvent
import com.obrigada_eu.poika.shared.ui.model.ImageButtonItem
import com.obrigada_eu.poika.shared.ui.model.TriangleButtonItem
import com.obrigada_eu.poika.shared.ui.screen.PlayerScreen
import org.jetbrains.compose.resources.stringResource
import poika.shared.generated.resources.*

@Composable
fun DesktopPlayerScreenHost(
    playerPresenter: PlayerPresenter,
) {
    val platform = platform()
    val context = LocalPlatformContext.current

    val emptySelectionText = stringResource(Res.string.select_at_least_one)
    val stringZeroZero = stringResource(Res.string._00_00)

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
                    println("uiEvent.collect = ShowSongDialog")
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
//                        Toaster.show(context, event.message, event.shortDuration)
                        println(event.message)
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
            Res.string.choose_song to playerPresenter::showChooseDialog,
            Res.string.delete_song to playerPresenter::showDeleteDialog,
            Res.string.help to playerPresenter::showHelpDialog
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
            .data(
                Res.getUri(
                    "drawable/logo_pink_${if (platform == "Desktop") "200_200" else "512_512"}.png"
                )
            ).build(),
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
                type = ChangeSpeedDirection.BACKWARD,
                label = stringResource(Res.string.minus_speed),
                icon = Icons.Filled.Remove,
                onClick = { playerPresenter.changeSpeed(ChangeSpeedDirection.BACKWARD) }),
            TriangleButtonItem(
                type = ChangeSpeedDirection.FORWARD,
                label = stringResource(Res.string.plus_speed),
                icon = Icons.Filled.Add,
                onClick = { playerPresenter.changeSpeed(ChangeSpeedDirection.FORWARD) }
            ),
        ),
        currentSpeed = currentSpeedText,

        playbackButtons = listOf(
            ImageButtonItem(
                label = stringResource(Res.string.minus_5_sec),
                icon = Icons.Filled.Replay5,
                weight = 2f,
                onClick = { playerPresenter.rewind(RewindDirection.BACKWARD) }
            ),
            ImageButtonItem(
                label = stringResource(if (isPlaying) Res.string.pause else Res.string.play),
                icon = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                weight = 4f,
                onClick = playerPresenter::togglePlayPause
            ),
            ImageButtonItem(
                label = stringResource(Res.string.stop),
                icon = Icons.Filled.Stop,
                weight = 3f,
                onClick = playerPresenter::stop
            ),
            ImageButtonItem(
                label = stringResource(Res.string.plus_5_sec),
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