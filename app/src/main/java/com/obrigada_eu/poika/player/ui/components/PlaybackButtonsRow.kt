package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.model.ImageButtonItem
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

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
fun PlaybackButtonsRowPreview() {
    PoikaTheme {
        PlaybackButtonsRow(PreviewData.playbackButtonsRow)
    }
}