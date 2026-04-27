package com.obrigada_eu.poika.player.ui.preview.screen

import android.content.res.Configuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.ui.screen.PlayerScreen
import com.obrigada_eu.poika.shared.presentation.player.formatters.TimeStringFormatter
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme
import poika.shared.generated.resources.Res


@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun PlayerScreenPreview() {
    val context = LocalPlatformContext.current

    PoikaTheme {
        PlayerScreen(
            menuItems = PreviewData.menuItems,
            menuExpanded = false,
            menuIconOnClick = {},
            onDismissMenuRequest = {},
            songTitle = PreviewData.songTitle,
            imageRequest = ImageRequest.Builder(context)
                .data(Res.getUri("drawable/logo_pink_200_200.png"))
                .build(),
            currentPositionText = TimeStringFormatter.formatSecToString(PreviewData.currentPos),
            trackDurationText = TimeStringFormatter.formatSecToString(PreviewData.duration),
            playbackSeekbarPosition = PreviewData.currentPos,
            playbackSeekbarMax = PreviewData.duration,
            onSeekChanged = {},
            onSeekReleased = {},
            playbackButtons = PreviewData.playbackButtonsRow,
            volumeStates = PreviewData.volumes,
            setVolume = { _, _ -> },
            showChooseSongDialog = false,
            showDeleteSongDialog = false,
            showDeleteConfirmationDialog = false,
            showHelpDialog = false,
            songs = emptyList(),
            selectedSongs = emptyList(),
            onLoadSong = {},
            onTryDeleteSongs = {},
            onConfirmDeleteSongs = {},
            onEmptySelection = {},
            onDismissChooseSongDialog = {},
            onDismissDeleteSongDialog = {},
            onDismissDeleteConfirmationDialog = {},
            changeSpeedButtons = PreviewData.changeSpeedButtons,
            currentSpeed = PreviewData.currentSpeed,
            snackbarHostState = SnackbarHostState(),
            snackbarMessage = null
        ) {}
    }
}