package com.obrigada_eu.poika.ui

import PlayerScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@PreviewLightDark
@Composable
fun PoikaRoot() {
    val isDarkTheme = isSystemInDarkTheme()

    PoikaTheme(useDarkTheme = isDarkTheme) {
        PlayerScreen()
    }
}