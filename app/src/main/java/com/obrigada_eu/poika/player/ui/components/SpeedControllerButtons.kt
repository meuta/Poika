package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.player.ui.components.SpeedControllerButtonType.*
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
fun SpeedControllerMinusButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    SpeedControllerButton(
        type = BACKWARD,
        text = text,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun SpeedControllerPlusButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    SpeedControllerButton(
        type = FORWARD,
        text = text,
        modifier = modifier,
        onClick = onClick
    )
}


class SpeedControllerButtonTextProvider : PreviewParameterProvider<String> {
    override val values = PreviewData.changeSpeedButtons.keys.asSequence()
}

@Composable
fun SpeedControllerButton(
    type: SpeedControllerButtonType,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        shape = when (type) {
            BACKWARD -> TriangleBackwardShape(Dimens.SpeedControllerButtonRoundRadius)
            FORWARD -> TriangleForwardShape(Dimens.SpeedControllerButtonRoundRadius)
        },
        onClick = onClick,
        modifier = modifier
            .width(Dimens.SpeedControllerButtonWidth)
            .height(Dimens.SpeedControllerButtonHeight),
        contentPadding = PaddingValues(),
    ) {
        Text(
            text = text,
            fontSize = Dimens.LargeFontSize,
            modifier = Modifier.offset(
                x = (Dimens.SpeedControllerButtonWidth / 7) * when (type) {
                    BACKWARD -> 1
                    FORWARD -> -1
                }
            ),
            maxLines = 1,
        )
    }
}

enum class SpeedControllerButtonType {
    BACKWARD, FORWARD
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
fun SpeedControllerButtonMinusPreview(
    @PreviewParameter(SpeedControllerButtonTextProvider::class) text: String,
) {
    PoikaTheme {
        SpeedControllerMinusButton(
            text = text,
            onClick = {},
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
fun SpeedControllerButtonPlusPreview(
    @PreviewParameter(SpeedControllerButtonTextProvider::class) text: String,
) {
    PoikaTheme {
        SpeedControllerPlusButton(
            text = text,
            onClick = {},
        )
    }
}

