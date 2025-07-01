package com.obrigada_eu.poika.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.LocalStringFormatter
import com.obrigada_eu.poika.ui.player.PlayerViewModel

@Composable
fun PlaybackSeekbar(
    playerViewModel: PlayerViewModel,
) {

    val stringFormatter = LocalStringFormatter.current

    val stringZeroZero = stringResource(R.string._00_00)
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }
    var currentPositionText by remember { mutableStateOf(stringZeroZero) }
    var trackDurationText by remember { mutableStateOf(stringZeroZero) }
    var playbackSeekbarMax by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        playerViewModel.progressStateUi.collect { progressState ->
            if (!isUserSeeking) {
                sliderPosition = progressState.currentPositionSec.toFloat()
                currentPositionText = progressState.currentPositionString
            }
            trackDurationText = progressState.durationString
            playbackSeekbarMax = progressState.durationSec.toFloat()
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = currentPositionText,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp),
            )
            Text(
                text = trackDurationText,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        CustomSlider(
            value = sliderPosition,
            onValueChange = { newValue ->
                isUserSeeking = true
                sliderPosition = newValue
                currentPositionText = stringFormatter.formatSecToString(newValue.toInt())
            },
            onValueChangeFinished = {
                playerViewModel.setSongProgress(sliderPosition.toInt())
                isUserSeeking = false
            },
            valueRange = 0f..playbackSeekbarMax,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}