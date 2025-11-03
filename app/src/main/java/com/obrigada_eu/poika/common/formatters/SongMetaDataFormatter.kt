package com.obrigada_eu.poika.common.formatters

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo

fun SongMetaData.toTitleString(): String = buildString {
    append("$artist - $title")
    voiceInstrument?.let { append(" (${it} version)") }
}

fun SongMetaData.toTrackInfoList(): List<TrackInfo> = tracks