import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.ui.components.ConfirmDeleteDialog
import com.obrigada_eu.poika.player.ui.components.HelpDialog
import com.obrigada_eu.poika.player.ui.components.ListDialog
import com.obrigada_eu.poika.player.ui.components.PlaybackButtonsRow
import com.obrigada_eu.poika.player.ui.components.PlaybackSeekbar
import com.obrigada_eu.poika.player.ui.components.PoikaTopAppBar
import com.obrigada_eu.poika.player.ui.components.VolumeSliderColumn
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PlayerScreen(
    menuItems: Map<String, () -> Unit>,
    menuExpanded: Boolean,
    menuIconOnClick: () -> Unit,
    onDismissMenuRequest: () -> Unit,
    songTitle: String,
    currentPositionText: String,
    trackDurationText: String,
    playbackSeekbarPosition: Float,
    playbackSeekbarMax: Float,
    onSeekChanged: (Float) -> Unit,
    onSeekReleased: () -> Unit,
    playbackButtons: Map<String, () -> Unit>,
    volumeStates: List<Float>,
    onSetVolume: (Int, Float) -> Unit,
    showChooseSongDialog: Boolean,
    showDeleteSongDialog: Boolean,
    showDeleteConfirmationDialog: Boolean,
    showHelpDialog: Boolean,
    songs: List<SongMetaData>,
    selectedSongs: List<SongMetaData>,
    onLoadSong: (SongMetaData) -> Unit,
    onTryDeleteSongs: (List<SongMetaData>) -> Unit,
    onConfirmDeleteSongs: () -> Unit,
    onEmptySelection: () -> Unit,
    onDismissChooseSongDialog: () -> Unit,
    onDismissDeleteSongDialog: () -> Unit,
    onDismissDeleteConfirmationDialog: () -> Unit,
    onDismissHelpDialog: () -> Unit,
) {
    Scaffold(
        topBar = {
            PoikaTopAppBar(
                menuItems = menuItems,
                menuIconOnclick = menuIconOnClick,
                onDismissRequest = onDismissMenuRequest,
                expanded = menuExpanded
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Song title
            Text(
                text = songTitle,
                textAlign = TextAlign.Center,
                fontSize = Dimens.MediumFontSize,
                lineHeight = Dimens.SongTitleLineHeight,
                letterSpacing = Dimens.SongTitleLetterSpacing,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(Dimens.SongTitlePadding),
            )

            // Playback Seekbar
            PlaybackSeekbar(
                currentPositionText = currentPositionText,
                trackDurationText = trackDurationText,
                sliderPosition = playbackSeekbarPosition,
                playbackSeekbarMax = playbackSeekbarMax,
                onValueChange = onSeekChanged,
                onValueChangeFinished = onSeekReleased,
            )

            // Buttons row
            PlaybackButtonsRow(playbackButtons)

            // Volume sliders
            VolumeSliderColumn(
                volumes = volumeStates,
                setVolume = onSetVolume
            )
        }
    }


    if (showChooseSongDialog) {
        ListDialog(
            title = stringResource(R.string.choose_song),
            items = songs,
            onConfirm = onLoadSong,
            onDismiss = onDismissChooseSongDialog,
        )
    }

    if (showDeleteSongDialog) {
        ListDialog(
            title = stringResource(R.string.delete_song),
            items = songs,
            isMultiChoice = true,
            onConfirmMultiChoice = onTryDeleteSongs,
            onEmptySelection = onEmptySelection,
            onDismiss = onDismissDeleteSongDialog,
        )
    }

    if (showDeleteConfirmationDialog) {
        ConfirmDeleteDialog(
            songs = selectedSongs,
            onConfirm = onConfirmDeleteSongs,
            onDismiss = onDismissDeleteConfirmationDialog,
        )
    }

    if (showHelpDialog) {
        HelpDialog(
            onDismiss = onDismissHelpDialog,
        )
    }
}

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
    PoikaTheme {
        PlayerScreen(
            menuItems = PreviewData.menuItems,
            menuExpanded = false,
            menuIconOnClick = {},
            onDismissMenuRequest = {},
            songTitle = PreviewData.songTitle,
            currentPositionText = TimeStringFormatter.formatSecToString(PreviewData.currentPos),
            trackDurationText = TimeStringFormatter.formatSecToString(PreviewData.duration),
            playbackSeekbarPosition = PreviewData.currentPos,
            playbackSeekbarMax = PreviewData.duration,
            onSeekChanged = {},
            onSeekReleased = {},
            playbackButtons = PreviewData.playbackButtons,
            volumeStates = PreviewData.volumes,
            onSetVolume = { _, _ ->},
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
            onDismissDeleteConfirmationDialog = {}
        ) {}
    }
}