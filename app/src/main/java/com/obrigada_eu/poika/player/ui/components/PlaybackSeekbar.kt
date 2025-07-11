package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter

@Composable
fun PlaybackSeekbar(
    currentPositionText: String,
    trackDurationText: String,
    sliderPosition: Float,
    playbackSeekbarMax: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
) {
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
            onValueChange = onValueChange,
            onValueChangeFinished = onValueChangeFinished,
            valueRange = 0f..playbackSeekbarMax,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PlaybackSeekbarPreview(
) {
    val currentPos = 34f
    val duration = 228f
    PlaybackSeekbar(
        currentPositionText = TimeStringFormatter.formatSecToString(currentPos),
        trackDurationText = TimeStringFormatter.formatSecToString(duration),
        sliderPosition = currentPos,
        playbackSeekbarMax = duration,
        onValueChange = {},
        onValueChangeFinished = {},
    )
}