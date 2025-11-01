package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.ui.theme.Dimens

private const val TitleWeight = 2f
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

class VolumeSliderProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("soprano", "alto", "minus")
}

@Preview(showBackground = true)
@Composable
fun VolumeSliderPreview(
    @PreviewParameter(VolumeSliderProvider::class) title: String
) {
    VolumeSlider(
        title = title,
        value = 0f
    ) {}
}