package com.obrigada_eu.poika.ui

import com.obrigada_eu.poika.domain.SongMetaData

class SongMetaDataMapper {

    fun mapToSongTitle(songMetaData: SongMetaData): String = buildString {
        append("${songMetaData.artist} - ${songMetaData.title}")
        songMetaData.voiceInstrument?.let { append(" (${it} version)") }
    }

    companion object {
        const val TAG = "SongMetaDataMapper"
    }
}