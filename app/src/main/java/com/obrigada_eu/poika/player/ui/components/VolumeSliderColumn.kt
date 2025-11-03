package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
fun VolumeSliderColumn(
    volumeStates: Map<String,Float>,
    setVolume: (String, Float) -> Unit,
) {
    
    Column {
        volumeStates.forEach { (part, volume) ->
            VolumeSlider(
                title = part,
                value = volume,
                onValueChange = { newValue -> setVolume(part, newValue) }
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
            volumeStates = PreviewData.volumes,
            setVolume = { _, _ -> }
        )
    }
}

private const val TAG = "VolumeSliderColumn"