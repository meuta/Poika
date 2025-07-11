package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.ui.theme.Dimens

@Composable
fun TimeText(time: String) {
    Text(
        text = time,
        fontSize = Dimens.MediumFontSize,
        modifier = Modifier.padding(Dimens.TimeTextPadding),
    )
}

class TimeTextProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("00:00")
}

@Preview(showBackground = true)
@Composable
fun TimeTextPreview(@PreviewParameter(TimeTextProvider::class) time: String) {
    TimeText(
        time = time,
    )
}