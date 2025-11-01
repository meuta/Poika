package com.obrigada_eu.poika.common.formatters

import com.obrigada_eu.poika.player.domain.model.SongMetaData

fun SongMetaData.toTitleString(): String = buildString {
    append("$artist - $title")
    voiceInstrument?.let { append(" (${it} version)") }
}