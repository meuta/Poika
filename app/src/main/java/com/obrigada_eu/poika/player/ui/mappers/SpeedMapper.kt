package com.obrigada_eu.poika.player.ui.mappers

import com.obrigada_eu.poika.common.formatters.SpeedStringFormatter

fun Float.toUi(stringFormatter: SpeedStringFormatter): String {
    val currentSpeedString = stringFormatter.formatSpeedToString(this)

    return currentSpeedString
}