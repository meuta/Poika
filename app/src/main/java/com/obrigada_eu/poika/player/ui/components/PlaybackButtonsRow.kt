package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.theme.Dimens

private const val ButtonWeight = 1f

@Composable
fun PlaybackButtonsRow(playbackButtons: Map<String, () -> Unit>) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(
                vertical = Dimens.PlayerButtonPaddingVertical,
                horizontal = Dimens.PlayerButtonPaddingHorizontal
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.PlayerButtonArrangement),
    ) {
        val buttons: Map<String, () -> Unit> = playbackButtons

        buttons.forEach {  (labelRes, action)  ->
            PlayerButton(
                text = labelRes,
                onClick = action,
                modifier = Modifier.weight(ButtonWeight),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaybackButtonsRowPreview() {
    PlaybackButtonsRow(mapOf(
        R.string.play to {},
        R.string.pause to {},
        R.string.stop to {}
    ).mapKeys { stringResource(it.key) })
}