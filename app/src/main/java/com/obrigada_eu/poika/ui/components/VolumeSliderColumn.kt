package com.obrigada_eu.poika.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.player.PlayerViewModel

@Composable
fun VolumeSliderColumn(
    playerViewModel: PlayerViewModel,
) {

    val volumeStates = remember { List(3) { mutableFloatStateOf(1f) } }

    LaunchedEffect(Unit) {
        playerViewModel.initialVolumeList.collect { list ->
            list.forEachIndexed { i, volume ->
                volumeStates[i].floatValue = volume
            }
        }
    }

    val titles = listOf(
        stringResource(R.string.soprano),
        stringResource(R.string.alto),
        stringResource(R.string.minus)
    )

    titles.forEachIndexed { i, title ->
        VolumeSlider(
            title = title,
            value = volumeStates[i].floatValue,
            onValueChange = {
                playerViewModel.setVolume(i, it)
                volumeStates[i].floatValue = it
            }
        )
    }
}