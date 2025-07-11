package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.ui.theme.Dimens


@Composable
fun CustomDropdownMenuItem(
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text,
                fontSize = Dimens.MediumFontSize,
                letterSpacing = Dimens.MenuItemLetterSpacing,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(horizontal = Dimens.MenuItemPadding),
            )
        },
        onClick = onClick,
    )
}

class DropdownMenuItemTextProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("Choose song")
}

@Preview(showBackground = true)
@Composable
fun CustomDropdownMenuItemPreview(
    @PreviewParameter(DropdownMenuItemTextProvider::class) text: String,
) {
    CustomDropdownMenuItem(
        text = text,
        onClick = {},
    )
}