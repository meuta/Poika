import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.ui.UiEvent
import com.obrigada_eu.poika.ui.components.ComposableHelpDialog
import com.obrigada_eu.poika.ui.components.ComposableListDialog
import com.obrigada_eu.poika.ui.components.ConfirmDeleteDialog
import com.obrigada_eu.poika.ui.components.CustomSlider
import com.obrigada_eu.poika.ui.components.PlayerButton
import com.obrigada_eu.poika.ui.components.PoikaTopAppBar
import com.obrigada_eu.poika.ui.components.VoiceSlider
import com.obrigada_eu.poika.ui.player.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(playerViewModel: PlayerViewModel) {

    val context = LocalContext.current
    val emptySelectionText = stringResource(R.string.select_at_least_one)

    var showChooseSongDialog by remember { mutableStateOf(false) }
    var showDeleteSongDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }

    var songs by remember { mutableStateOf<List<SongMetaData>>(emptyList()) }
    var selectedSongs by remember { mutableStateOf<List<SongMetaData>>(emptyList()) }

    var playbackPosition by remember { mutableFloatStateOf(0f) }
    var voiceOne by remember { mutableFloatStateOf(100f) }
    var voiceTwo by remember { mutableFloatStateOf(100f) }
    var minusTrack by remember { mutableFloatStateOf(100f) }


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
            Text(
                text = stringResource(R.string.to_start_singing_practice_),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(32.dp),
            )

            // Row for current position and duration
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string._00_00),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                )
                Text(
                    text = stringResource(R.string._00_00),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                )
            }

            // Playback slider
            CustomSlider(
                value = playbackPosition,
                onValueChange = { playbackPosition = it },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth(),
            )

            // Buttons row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                PlayerButton(
                    text = stringResource(R.string.play),
                    onClick = { /* handle play */ },
                    modifier = Modifier
                        .weight(1f),
                )
                PlayerButton(
                    text = stringResource(R.string.pause),
                    onClick = { /* handle pause */ },
                    modifier = Modifier
                        .weight(1f),
                )
                PlayerButton(
                    text = stringResource(R.string.stop),
                    onClick = { /* handle stop */ },
                    modifier = Modifier
                        .weight(1f),
                )
            }

            // Voice sliders
            VoiceSlider(
                title = stringResource(R.string.soprano),
                value = voiceOne,
                onValueChange = { voiceOne = it },
            )

            VoiceSlider(
                title = stringResource(R.string.alto),
                value = voiceTwo,
                onValueChange = { voiceTwo = it },
            )
            VoiceSlider(
                title = stringResource(R.string.minus),
                value = minusTrack,
                onValueChange = { minusTrack = it },
            )
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



