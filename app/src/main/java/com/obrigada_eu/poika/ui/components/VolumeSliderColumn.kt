package com.obrigada_eu.poika.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.player.PlayerViewModel

@Composable
fun VolumeSliderColumn(
    playerViewModel: PlayerViewModel,
) {

    val volumeList by playerViewModel.initialVolumeList.collectAsState()

    VolumeSlider(
        title = stringResource(R.string.soprano),
        value = volumeList[0],
        onValueChange = {
            playerViewModel.setVolume(0, it)
        },
    )
    VolumeSlider(
        title = stringResource(R.string.alto),
        value = volumeList[1],
        onValueChange = {
            playerViewModel.setVolume(1, it)
        },
    )
    VolumeSlider(
        title = stringResource(R.string.minus),
        value = volumeList[2],
        onValueChange = {
            playerViewModel.setVolume(2, it)
        },
    )
}