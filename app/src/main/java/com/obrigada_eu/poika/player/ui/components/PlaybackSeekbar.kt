package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.common.formatters.TimeStringFormatter
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme
import kotlin.String

@Composable
fun PlaybackSeekbar(
    currentPositionText: String,
    trackDurationText: String,
    sliderPosition: Float,
    playbackSeekbarMax: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    changeSpeedButtons: Map<String, () -> Unit>,
    currentSpeedText: String,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TimeText(currentPositionText)

            SpeedController(
                changeSpeedButtons = changeSpeedButtons,
                currentSpeedText = currentSpeedText
            )

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

@Composable
fun SpeedController(
    changeSpeedButtons: Map<String, () -> Unit>,
    currentSpeedText: String,
) {
    Row(
        modifier = Modifier
            .border(
                BorderStroke(
                    width = Dimens.SpeedControllerBorderWidth,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            .padding(horizontal = Dimens.SpeedControllerPaddingHorizontal),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpeedControllerButtonPaddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpeedControllerMinusButton(
            text = changeSpeedButtons.keys.first(),
            onClick = changeSpeedButtons.values.first(),
        )

        Text(
            text = "speed\n$currentSpeedText",
            fontSize = Dimens.SmallFontSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )

        SpeedControllerPlusButton(
            text = changeSpeedButtons.keys.last(),
            onClick = changeSpeedButtons.values.last(),
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