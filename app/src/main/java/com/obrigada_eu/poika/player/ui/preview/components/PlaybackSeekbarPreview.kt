package com.obrigada_eu.poika.player.ui.preview.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.presentation.player.formatters.TimeStringFormatter
import com.obrigada_eu.poika.shared.ui.components.PlaybackSeekbar
import com.obrigada_eu.poika.shared.ui.components.SpeedController
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme


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
fun PlaybackSeekbarPreview() {
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
            changeSpeedButtons = PreviewData.changeSpeedButtons,
            currentSpeedText = PreviewData.currentSpeed,
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
fun SpeedControllerPreview() {
    PoikaTheme {
        SpeedController(
            changeSpeedButtons = PreviewData.changeSpeedButtons,
            currentSpeedText = PreviewData.currentSpeed,
        )
    }
}