package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.obrigada_eu.poika.shared.domain.audio.ChangeSpeedDirection
import com.obrigada_eu.poika.shared.ui.theme.Dimens

@Composable
fun SpeedControllerButton(
    type: ChangeSpeedDirection,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val radius = Dimens.SpeedControllerButtonRoundRadius
    val (shape, direction) = when (type) {
        ChangeSpeedDirection.BACKWARD -> listOf(TriangleBackwardShape(radius), 1)
        ChangeSpeedDirection.FORWARD -> listOf(TriangleForwardShape(radius), -1)
    }

    Button(
        shape = shape as Shape,
        onClick = onClick,
        modifier = modifier
            .width(Dimens.SpeedControllerButtonWidth)
            .height(Dimens.SpeedControllerButtonHeight),
        contentPadding = PaddingValues(),
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier
                .size(Dimens.SpeedControllerButtonIconSize)
                .offset(x = (Dimens.SpeedControllerButtonWidth / 7) * direction as Int),
            contentDescription = label
        )
    }
}