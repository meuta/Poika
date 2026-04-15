package com.obrigada_eu.poika.player.ui.mappers

import com.obrigada_eu.poika.player.ui.formatters.SpeedStringFormatter

fun Float.toSpeedString(stringFormatter: SpeedStringFormatter): String {
    val currentSpeedString = stringFormatter.formatSpeedToString(this)

    return currentSpeedString
}