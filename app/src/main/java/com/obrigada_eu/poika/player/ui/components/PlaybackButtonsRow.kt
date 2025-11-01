package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.player.ui.PlayerViewModel
import com.obrigada_eu.poika.ui.theme.Dimens

private const val ButtonWeight = 1f

@Composable
fun PlaybackButtonsRow(playerViewModel: PlayerViewModel) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(
                vertical = Dimens.PlayerButtonPaddingVertical,
                horizontal = Dimens.PlayerButtonPaddingHorizontal
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.PlayerButtonArrangement),
    ) {

        val buttons: Map<String, () -> Unit> = mapOf(
            R.string.play to { playerViewModel.play() },
            R.string.pause to { playerViewModel.pause() },
            R.string.stop to { playerViewModel.stop() }
        ).mapKeys { stringResource(it.key) }

        buttons.forEach {  (labelRes, action)  ->
            PlayerButton(
                text = labelRes,
                onClick = action,
                modifier = Modifier.weight(ButtonWeight),
            )
        }
    }
}