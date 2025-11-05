package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

private const val TitleWeight = 3f
private const val TrackWeight = 7f

@Composable
fun VolumeSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontSize = Dimens.MediumFontSize,
            letterSpacing = Dimens.VolumeTitleLetterSpacing,
            maxLines = 1,
            modifier = Modifier
                .weight(TitleWeight)
                .padding(vertical = Dimens.VolumeTitlePaddingVertical),
            color = MaterialTheme.colorScheme.onBackground,
        )
        CustomSlider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier
                .weight(TrackWeight),
        )
    }
}

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