package com.obrigada_eu.poika.player.domain.repository

import android.net.Uri
import com.obrigada_eu.poika.player.domain.model.SongMetaData

interface SongRepository {
    fun importSong(uri: Uri): SongMetaData?
    fun getAllSongsMetadata(): List<SongMetaData>
    fun deleteSong(song: SongMetaData): Boolean
}