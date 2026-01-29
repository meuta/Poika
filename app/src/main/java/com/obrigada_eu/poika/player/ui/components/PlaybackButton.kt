package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.player.ui.model.ImageButtonItem
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
fun PlaybackButton(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        shape = RoundedCornerShape(Dimens.PlaybackButtonRoundedCornerShapePercent),
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = Dimens.PlaybackButtonPaddingHorizontal),
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(Dimens.PlaybackButtonIconSize),
            contentDescription = label
        )
    }
}

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