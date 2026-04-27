package com.obrigada_eu.poika.shared.ui.components

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
import com.obrigada_eu.poika.shared.ui.model.TriangleButtonItem
import com.obrigada_eu.poika.shared.ui.theme.Dimens

@Composable
fun PlaybackSeekbar(
    currentPositionText: String,
    trackDurationText: String,
    sliderPosition: Float,
    playbackSeekbarMax: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    changeSpeedButtons: Pair<TriangleButtonItem, TriangleButtonItem>,
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
    changeSpeedButtons: Pair<TriangleButtonItem, TriangleButtonItem>,
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
        SpeedControllerButton(
            type = changeSpeedButtons.first.type,
            label = changeSpeedButtons.first.label,
            icon = changeSpeedButtons.first.icon,
            onClick = changeSpeedButtons.first.onClick,
        )

        Text(
            text = "speed\n$currentSpeedText",
            fontSize = Dimens.SmallFontSize,
            textAlign = TextAlign.Center,
            lineHeight = Dimens.SpeedControllerTextLineHeight,
            color = MaterialTheme.colorScheme.onBackground,
        )

        SpeedControllerButton(
            type = changeSpeedButtons.second.type,
            label = changeSpeedButtons.second.label,
            icon = changeSpeedButtons.second.icon,
            onClick = changeSpeedButtons.second.onClick,
        )
    }
}