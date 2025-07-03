package com.obrigada_eu.poika.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.player.PlayerViewModel

@Composable
fun PlaybackButtonsRow(playerViewModel: PlayerViewModel) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PlayerButton(
            text = stringResource(R.string.play),
            onClick = { playerViewModel.play() },
            modifier = Modifier.Companion
                .weight(1f),
        )
        PlayerButton(
            text = stringResource(R.string.pause),
            onClick = { playerViewModel.pause() },
            modifier = Modifier.Companion
                .weight(1f),
        )
        PlayerButton(
            text = stringResource(R.string.stop),
            onClick = { playerViewModel.stop() },
            modifier = Modifier.Companion
                .weight(1f),
        )
    }
}