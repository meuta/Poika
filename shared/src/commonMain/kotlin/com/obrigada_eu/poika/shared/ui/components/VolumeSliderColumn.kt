package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

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