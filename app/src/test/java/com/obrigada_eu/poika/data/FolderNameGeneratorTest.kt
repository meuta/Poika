package com.obrigada_eu.poika.data

import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.domain.TrackInfo
import org.junit.Test
import org.junit.Assert.*

class FolderNameGeneratorTest {

    @Test
    fun `from should generate folder name from metadata`() {
        val meta = SongMetaData(
            artist = "Moby",
            title = "Natural Blues",
            tracks = listOf(TrackInfo("123", "456")),
            folderName = "567"
        )
        val result = FolderNameGenerator.from(meta)
        assertEquals("moby_natural_blues", result)
    }


//    @Test
//    fun `from should remove non-alphanumeric characters`() {
//        val meta = SongMetaData(
//            artist = "Daft Punk",
//            title = "Harder, Better, Faster, Stronger",
//            tracks = listOf(TrackInfo("", "")),
//            folderName = ""
//        )
//        val result = FolderNameGenerator.from(meta)
//        assertEquals("daft_punk_harder_better_faster_stronger", result)
//    }

    @Test
    fun `from should remove non-alphanumeric characters`() {
        val meta = SongMetaData(
            artist = "Amália Rodrigues",
            title = "Uma Casa Portuguesa",
            tracks = listOf(TrackInfo("", "")),
            folderName = ""
        )
        val result = FolderNameGenerator.from(meta)
        assertEquals("amlia_rodrigues_uma_casa_portuguesa", result)
    }
}