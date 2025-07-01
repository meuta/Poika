package com.obrigada_eu.poika.ui

import PlayerScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.obrigada_eu.poika.ui.player.PlayerViewModel
import com.obrigada_eu.poika.ui.player.StringFormatter
import com.obrigada_eu.poika.ui.theme.PoikaTheme


@Composable
fun PoikaRoot(
    playerViewModel: PlayerViewModel,
    stringFormatter: StringFormatter,
) {
    val isDarkTheme = isSystemInDarkTheme()

    CompositionLocalProvider(
        LocalStringFormatter provides stringFormatter
    ) {
        PoikaTheme(useDarkTheme = isDarkTheme) {
            PlayerScreen(playerViewModel = playerViewModel)
        }
    }
}