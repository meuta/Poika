package com.obrigada_eu.poika.player.domain.repository

import com.obrigada_eu.poika.player.domain.model.SongMetaData

interface SongRepository {
    fun getAllSongsMetadata(): List<SongMetaData>
}