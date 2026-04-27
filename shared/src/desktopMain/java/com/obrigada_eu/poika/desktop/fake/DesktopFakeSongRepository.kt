package com.obrigada_eu.poika.desktop.fake

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.model.TrackInfo
import com.obrigada_eu.poika.shared.domain.repository.SongRepository

class DesktopFakeSongRepository : SongRepository {
    override fun importSong(uriString: String): SongMetaData? {

        return SongMetaData(
            artist = "Some Artist",
            title = "Desktop Song",
            voiceInstrument = "piano",
            tracks = listOf(
                TrackInfo("file1", "track1"),
                TrackInfo("file2", "track2"),
                TrackInfo("file3", "track3")
            ),
            folderName = "FolferName"
        )
    }

    override fun getAllSongsMetadata(): List<SongMetaData> {
        return listOf(
            SongMetaData(
                title = "Desktop Song 1",
                artist = "Artist 1",
                voiceInstrument = "piano",
                tracks = listOf(TrackInfo("desktopFile1", "track1"), TrackInfo("desktopFile2", "track2")),
                folderName = "folderName 1"
            ),
            SongMetaData(
                title = "Desktop Song 2",
                artist = "Artist 2",
                voiceInstrument = "voice",
                tracks = listOf(TrackInfo("desktopFile3", "track1"), TrackInfo("desktopFile4", "track4")),
                folderName = "folderName 2"
            )
        )
    }

    override fun deleteSong(song: SongMetaData): Boolean {
        return true
    }
}