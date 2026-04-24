package com.obrigada_eu.poika.shared.presentation.player.mappers

import com.obrigada_eu.poika.shared.presentation.player.formatters.SpeedStringFormatter

fun Float.toSpeedString(stringFormatter: SpeedStringFormatter): String {
    val currentSpeedString = stringFormatter.formatSpeedToString(this)

    return currentSpeedString
}