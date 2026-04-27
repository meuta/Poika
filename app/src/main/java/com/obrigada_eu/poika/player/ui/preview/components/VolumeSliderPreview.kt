package com.obrigada_eu.poika.player.ui.preview.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.ui.components.VolumeSlider
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme

class VolumeSliderTitleProvider : PreviewParameterProvider<String> {
    override val values = PreviewData.volumeSliderTitles.asSequence()
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
fun VolumeSliderPreview(
    @PreviewParameter(VolumeSliderTitleProvider ::class) title: String,
) {
    PoikaTheme {
        VolumeSlider(
            title = title,
            value = 0f
        ) {}
    }
}