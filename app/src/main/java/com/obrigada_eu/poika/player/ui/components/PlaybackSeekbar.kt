package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.PoikaTheme

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


@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun PlaybackSeekbarPreview(
) {
    val currentPos = PreviewData.currentPos
    val duration = PreviewData.duration
    PoikaTheme {
        PlaybackSeekbar(
            currentPositionText = TimeStringFormatter.formatSecToString(currentPos),
            trackDurationText = TimeStringFormatter.formatSecToString(duration),
            sliderPosition = currentPos,
            playbackSeekbarMax = duration,
            onValueChange = {},
            onValueChangeFinished = {},
        )
    }
}