package com.obrigada_eu.poika.shared.fake

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.repository.SongRepository

class FakeSongRepository() : SongRepository {

    var songs = listOf<SongMetaData>()
    var songToReturn: SongMetaData? = null
    var lastImportedUri: String? = null

    var shouldDeleteSucceed = true
    var lastDeletedSong: SongMetaData? = null


    override fun importSong(uriString: String): SongMetaData? {
        lastImportedUri = uriString
        return songToReturn
    }

    override fun getAllSongsMetadata(): List<SongMetaData> {
        return songs
    }


    override fun deleteSong(song: SongMetaData): Boolean {
        lastDeletedSong = song
        return shouldDeleteSucceed
    }
}