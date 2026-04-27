package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.obrigada_eu.poika.shared.ui.model.ImageButtonItem
import com.obrigada_eu.poika.shared.ui.theme.Dimens
import kotlin.collections.forEach

@Composable
fun PlaybackButtonsRow(playbackButtons: List<ImageButtonItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Dimens.PlaybackButtonPaddingTop,
                bottom = Dimens.PlaybackButtonPaddingBottom,
                start = Dimens.PlaybackButtonPaddingHorizontal,
                end = Dimens.PlaybackButtonPaddingHorizontal
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.PlaybackButtonArrangement),
    ) {
        playbackButtons.forEach { (label, icon, weight, action) ->
            PlaybackButton(
                label = label,
                icon = icon,
                onClick = action,
                modifier = Modifier.weight(
                    weight = weight,
                ),
            )
        }
    }
}