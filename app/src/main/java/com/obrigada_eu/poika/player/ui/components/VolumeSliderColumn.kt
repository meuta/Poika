package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.R

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


@Preview(showBackground = true)
@Composable
fun VolumeSliderColumnPreview() {
    VolumeSliderColumn(
        volumes = listOf(1f, 0.58f, 0.35f),
        setVolume = { _, _ -> }
    )
}