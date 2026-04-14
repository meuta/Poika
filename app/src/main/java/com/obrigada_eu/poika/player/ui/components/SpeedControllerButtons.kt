package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.player.ui.components.SpeedControllerButtonType.*
import com.obrigada_eu.poika.player.ui.model.TriangleButtonItem
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme


@Composable
fun SpeedControllerButton(
    type: SpeedControllerButtonType,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val radius = Dimens.SpeedControllerButtonRoundRadius
    val (shape, direction) = when (type) {
        BACKWARD -> listOf(TriangleBackwardShape(radius), 1)
        FORWARD -> listOf(TriangleForwardShape(radius), -1)
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

enum class SpeedControllerButtonType {
    BACKWARD, FORWARD
}


class SpeedControllerButtonItemProvider : PreviewParameterProvider<TriangleButtonItem> {
    override val values = PreviewData.changeSpeedButtons.toList().asSequence()
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
fun SpeedControllerButtonsPreview(
    @PreviewParameter(SpeedControllerButtonItemProvider::class) button: TriangleButtonItem,
) {
    PoikaTheme {
        SpeedControllerButton(
            type = button.type,
            label = button.label,
            icon = button.icon,
            onClick = {}
        )
    }
}



