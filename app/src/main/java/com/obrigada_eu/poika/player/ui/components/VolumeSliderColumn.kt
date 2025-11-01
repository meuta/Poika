package com.obrigada_eu.poika.player.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.player.ui.PlayerViewModel

@Composable
fun VolumeSliderColumn(
    playerViewModel: PlayerViewModel,
) {

    val volumeStates by playerViewModel.volumeList.collectAsState()

    val titles = listOf(
        stringResource(R.string.soprano),
        stringResource(R.string.alto),
        stringResource(R.string.minus)
    )

    titles.forEachIndexed { index, title ->
        VolumeSlider(
            title = title,
            value = volumeStates[index],
            onValueChange = {
                playerViewModel.setVolume(index, it)
            }
        )
    }
}