import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.ui.utils.Toaster
import com.obrigada_eu.poika.player.ui.model.UiEvent
import com.obrigada_eu.poika.player.ui.components.HelpDialog
import com.obrigada_eu.poika.player.ui.components.ListDialog
import com.obrigada_eu.poika.player.ui.components.ConfirmDeleteDialog
import com.obrigada_eu.poika.player.ui.components.PlaybackButtonsRow
import com.obrigada_eu.poika.player.ui.components.PlaybackSeekbar
import com.obrigada_eu.poika.player.ui.components.PoikaTopAppBar
import com.obrigada_eu.poika.player.ui.components.VolumeSliderColumn
import com.obrigada_eu.poika.player.ui.PlayerViewModel
import com.obrigada_eu.poika.ui.theme.Dimens
import kotlinx.coroutines.flow.firstOrNull

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PlayerScreen(
    playerViewModel: PlayerViewModel,
) {
    val context = LocalContext.current

    val emptySelectionText = stringResource(R.string.select_at_least_one)

    var showChooseSongDialog by remember { mutableStateOf(false) }
    var showDeleteSongDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }

    var songs by remember { mutableStateOf<List<SongMetaData>>(emptyList()) }
    var selectedSongs by remember { mutableStateOf<List<SongMetaData>>(emptyList()) }

    var menuExpanded by remember { mutableStateOf(false) }

    val songTitle by playerViewModel.songTitleText.collectAsState()

    val stringZeroZero = stringResource(R.string._00_00)

    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }
    var currentPositionText by remember { mutableStateOf(stringZeroZero) }
    var trackDurationText by remember { mutableStateOf(stringZeroZero) }
    var playbackSeekbarMax by remember { mutableFloatStateOf(0f) }

    val volumeStates = remember { List(3) { mutableFloatStateOf(1f) } }


    Scaffold(
        topBar = {
            val menuItems: Map<String, () -> Unit> = mapOf(
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
                }

            PoikaTopAppBar(
                menuItems = menuItems,
                menuIconOnclick = { menuExpanded = !menuExpanded },
                onDismissRequest = { menuExpanded = false },
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
                text = songTitle ?: stringResource(R.string.to_start_singing_practice_),
                textAlign = TextAlign.Center,
                fontSize = Dimens.MediumFontSize,
                lineHeight = Dimens.SongTitleLineHeight,
                letterSpacing = Dimens.SongTitleLetterSpacing,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(Dimens.SongTitlePadding),
            )


            PlaybackSeekbar(
                currentPositionText = currentPositionText,
                trackDurationText = trackDurationText,
                sliderPosition = sliderPosition,
                playbackSeekbarMax = playbackSeekbarMax,
                onValueChange = { newValue ->
                    isUserSeeking = true
                    sliderPosition = newValue
                    currentPositionText = TimeStringFormatter.formatSecToString(newValue)
                },
                onValueChangeFinished = {
                    playerViewModel.setSongProgress(sliderPosition)
                    isUserSeeking = false
                },
            )

            // Buttons row
            val playbackButtons: Map<String, () -> Unit> = mapOf(
                R.string.play to { playerViewModel.play() },
                R.string.pause to { playerViewModel.pause() },
                R.string.stop to { playerViewModel.stop() }
            ).mapKeys { stringResource(it.key) }

            PlaybackButtonsRow(playbackButtons)

            // Volume sliders
            VolumeSliderColumn(
                volumes = volumeStates.map { it.floatValue },
                setVolume = { index, value ->
                    playerViewModel.setVolume(index, value)
                    volumeStates[index].floatValue = value
                }
            )
        }
    }

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
                sliderPosition = progressState.currentPositionSec
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

    if (showChooseSongDialog) {
        ListDialog(
            title = stringResource(R.string.choose_song),
            items = songs,
            onConfirm = playerViewModel::loadSong,
            onDismiss = { showChooseSongDialog = false },
        )
    }

    if (showDeleteSongDialog) {
        ListDialog(
            title = stringResource(R.string.delete_song),
            items = songs,
            isMultiChoice = true,
            onConfirmMultiChoice = { selected ->
                selectedSongs = selected
                showDeleteSongDialog = false
                showDeleteConfirmationDialog = true
            },
            onEmptySelection = {
                playerViewModel.showMessage(emptySelectionText)
            },
            onDismiss = {
                showDeleteSongDialog = false
                selectedSongs = emptyList()
            },
        )
    }

    if (showDeleteConfirmationDialog) {
        ConfirmDeleteDialog(
            songs = selectedSongs,
            onConfirm = {
                playerViewModel.deleteSongs(selectedSongs)
                showDeleteConfirmationDialog = false
                selectedSongs = emptyList()
            },
            onDismiss = {
                showDeleteConfirmationDialog = false
                selectedSongs = emptyList()
            },
        )
    }

    if (showHelpDialog) {
        HelpDialog(
            onDismiss = {
                showHelpDialog = false
            },
        )
    }
}