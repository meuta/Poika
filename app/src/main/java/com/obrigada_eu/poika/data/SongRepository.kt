package com.obrigada_eu.poika.data

import com.obrigada_eu.poika.domain.SongMetaData

interface SongRepository {
    fun getAllSongsMetadata(): List<SongMetaData>
}
