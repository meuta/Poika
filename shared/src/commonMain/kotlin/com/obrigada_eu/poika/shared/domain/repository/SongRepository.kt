package com.obrigada_eu.poika.shared.domain.repository

import com.obrigada_eu.poika.shared.domain.model.SongMetaData

interface SongRepository {
    fun importSong(uriString: String): SongMetaData?
    fun getAllSongsMetadata(): List<SongMetaData>
    fun deleteSong(song: SongMetaData): Boolean
}