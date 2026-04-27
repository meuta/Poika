package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.obrigada_eu.poika.shared.ui.theme.Dimens


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