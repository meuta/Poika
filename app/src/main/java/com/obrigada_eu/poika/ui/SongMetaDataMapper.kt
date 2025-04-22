package com.obrigada_eu.poika.ui

import com.obrigada_eu.poika.domain.SongMetaData

class SongMetaDataMapper {
    fun mapToSongTitle(songMetaData: SongMetaData): String {
        return "${songMetaData.artist} - ${songMetaData.title}"
    }

    companion object {
        const val TAG = "SongMetaDataMapper"
    }
}