package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

private const val WeightSmall = 1f
private const val WeightBig = 2f

@Composable
fun PlaybackButtonsRow(playbackButtons: Map<String, () -> Unit>) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(
                vertical = Dimens.PlaybackButtonPaddingVertical,
                horizontal = Dimens.PlaybackButtonPaddingHorizontal
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.PlaybackButtonArrangement),
    ) {
        playbackButtons.forEach { (labelRes, action)  ->
            PlaybackButton(
                text = labelRes,
                onClick = action,
                modifier = Modifier.weight(if (labelRes.length < 4) WeightSmall else WeightBig),
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
        PlaybackButtonsRow(PreviewData.playbackButtons)
    }
}