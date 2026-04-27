package com.obrigada_eu.poika.player.ui.preview.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.shared.ui.model.ImageButtonItem
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.ui.components.PlaybackButton
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme


class PlaybackButtonItemProvider : PreviewParameterProvider<ImageButtonItem> {
    override val values = PreviewData.playbackButtons.asSequence()
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
fun PlaybackButtonPreview(
    @PreviewParameter(PlaybackButtonItemProvider::class) buttonItem: ImageButtonItem,
) {
    PoikaTheme {
        PlaybackButton(
            label = buttonItem.label,
            onClick = {},
            icon = buttonItem.icon,
            modifier = Modifier,
        )
    }
}