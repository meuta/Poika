package com.obrigada_eu.poika.player.ui.preview.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.ui.components.SpeedControllerButton
import com.obrigada_eu.poika.shared.ui.model.TriangleButtonItem
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme

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



