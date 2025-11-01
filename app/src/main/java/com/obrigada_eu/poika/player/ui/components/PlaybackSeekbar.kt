package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.ui.PlayerViewModel

@Composable
fun PlaybackSeekbar(
    playerViewModel: PlayerViewModel,
) {

    val stringFormatter = TimeStringFormatter

    val stringZeroZero = stringResource(R.string._00_00)
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }
    var currentPositionText by remember { mutableStateOf(stringZeroZero) }
    var trackDurationText by remember { mutableStateOf(stringZeroZero) }
    var playbackSeekbarMax by remember { mutableFloatStateOf(0f) }

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

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TimeText(currentPositionText)
            TimeText(trackDurationText)
        }

        CustomSlider(
            value = sliderPosition,
            onValueChange = { newValue ->
                isUserSeeking = true
                sliderPosition = newValue
                currentPositionText = stringFormatter.formatSecToString(newValue)
            },
            onValueChangeFinished = {
                playerViewModel.setSongProgress(sliderPosition)
                isUserSeeking = false
            },
            valueRange = 0f..playbackSeekbarMax,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}