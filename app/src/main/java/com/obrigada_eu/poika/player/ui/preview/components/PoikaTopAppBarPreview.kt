package com.obrigada_eu.poika.player.ui.preview.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.ui.components.PoikaTopAppBar
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun PoikaTopAppBarPreview() {
    PoikaTheme {
        PoikaTopAppBar(
            menuItems = PreviewData.menuItems,
            menuIconOnclick = {},
            onDismissRequest = {},
            expanded = true,
        )
    }
}