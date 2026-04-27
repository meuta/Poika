package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.obrigada_eu.poika.shared.ui.theme.Dimens


@Composable
fun CustomDropdownMenuItem(
    text: String,
    onClick: () -> Unit,
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