import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.ui.UiEvent
import com.obrigada_eu.poika.ui.components.ComposableHelpDialog
import com.obrigada_eu.poika.ui.components.ComposableListDialog
import com.obrigada_eu.poika.ui.components.ConfirmDeleteDialog
import com.obrigada_eu.poika.ui.components.PlaybackButtonsRow
import com.obrigada_eu.poika.ui.components.PlaybackSeekbar
import com.obrigada_eu.poika.ui.components.PoikaTopAppBar
import com.obrigada_eu.poika.ui.components.VolumeSliderColumn
import com.obrigada_eu.poika.ui.player.PlayerViewModel

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

    val songTitle by playerViewModel.songTitleText.collectAsState()

    Scaffold(
        topBar = {
            PoikaTopAppBar(playerViewModel = playerViewModel)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Song title
            Text(
                text = songTitle ?: stringResource(R.string.to_start_singing_practice_),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(32.dp),
            )

            // Playback seekbar
            PlaybackSeekbar(playerViewModel)

            // Buttons row
            PlaybackButtonsRow(playerViewModel)

            // Volume sliders
            VolumeSliderColumn(playerViewModel)
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
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is UiEvent.ShowHelpDialog -> {
                    showHelpDialog = true
                }
            }
        }
    }

    if (showChooseSongDialog) {
        ComposableListDialog(
            title = stringResource(R.string.choose_song),
            items = songs,
            onConfirm = playerViewModel::loadSong,
            onDismiss = { showChooseSongDialog = false },
        )
    }

    if (showDeleteSongDialog) {
        ComposableListDialog(
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
        ComposableHelpDialog(
            onDismiss = {
                showHelpDialog = false
            },
        )
    }
}



