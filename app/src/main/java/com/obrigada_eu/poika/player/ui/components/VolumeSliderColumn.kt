package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
fun VolumeSliderColumn(
    volumes: List<Float>,
    setVolume: (Int, Float) -> Unit
) {

    val titles = listOf(
        stringResource(R.string.soprano),
        stringResource(R.string.alto),
        stringResource(R.string.minus)
    )

    Column {
        titles.forEachIndexed { index, title ->
            VolumeSlider(
                title = title,
                value = volumes[index],
                onValueChange = {
                    setVolume(index, it)
                }
            )
        }
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
fun VolumeSliderColumnPreview() {
    PoikaTheme {
        VolumeSliderColumn(
            volumes = PreviewData.volumes,
            setVolume = { _, _ -> }
        )
    }
}