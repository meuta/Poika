package com.obrigada_eu.poika.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.obrigada_eu.poika.ui.player.StringFormatter

val LocalStringFormatter = staticCompositionLocalOf<StringFormatter> {
    error("No StringFormatter provided")
}