package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
fun TimeText(time: String) {
    Text(
        text = time,
        fontSize = Dimens.MediumFontSize,
        modifier = Modifier.padding(horizontal = Dimens.TimeTextPaddingHorizontal),
        color = MaterialTheme.colorScheme.onBackground,
    )
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
fun TimeTextPreview() {
    PoikaTheme {
        TimeText(
            time = PreviewData.timeStringZeroZero,
        )
    }
}