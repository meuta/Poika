package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme


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
fun CustomDropdownMenuItemPreview() {
    PoikaTheme {
        CustomDropdownMenuItem(
            text = PreviewData.dropdownMenuItemText,
            onClick = {},
        )
    }
}