package com.obrigada_eu.poika.ui

import PlayerScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.obrigada_eu.poika.ui.player.PlayerViewModel
import com.obrigada_eu.poika.ui.theme.PoikaTheme

@Composable
fun PoikaRoot(playerViewModel: PlayerViewModel) {
    val isDarkTheme = isSystemInDarkTheme()

    PoikaTheme(useDarkTheme = isDarkTheme) {
        PlayerScreen(playerViewModel = playerViewModel)
    }
}