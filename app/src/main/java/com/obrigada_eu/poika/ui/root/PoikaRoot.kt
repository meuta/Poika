package com.obrigada_eu.poika.ui.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.obrigada_eu.poika.player.ui.screen.PlayerScreenHost
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.ui.theme.PoikaTheme


@Composable
fun PoikaRoot(
    playerPresenter: PlayerPresenter,
) {
    val isDarkTheme = isSystemInDarkTheme()
    PoikaTheme(useDarkTheme = isDarkTheme) {
        PlayerScreenHost(playerPresenter = playerPresenter)
    }
}