package com.obrigada_eu.poika.shared.presentation.player.mappers

import com.obrigada_eu.poika.shared.domain.model.SongMetaData

fun SongMetaData.toTitleString(): String = buildString {
    append("$artist - $title")
    voiceInstrument?.let { append(" (${it} version)") }
}