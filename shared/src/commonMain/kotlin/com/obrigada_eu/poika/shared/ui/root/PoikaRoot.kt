package com.obrigada_eu.poika.shared.ui.root

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.shared.ui.screen.PlayerScreenHost
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme


@Composable
fun PoikaRoot(
    playerPresenter: PlayerPresenter,
    onImportZip: (() -> Unit)? = null
) {
    val isDarkTheme = isSystemInDarkTheme()
    PoikaTheme(useDarkTheme = isDarkTheme) {
        PlayerScreenHost(playerPresenter = playerPresenter, onImportZip)
    }
}