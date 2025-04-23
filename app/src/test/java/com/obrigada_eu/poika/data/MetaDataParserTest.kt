package com.obrigada_eu.poika.data

import com.obrigada_eu.poika.domain.TrackInfo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

class MetaDataParserTest {

    private lateinit var parser: MetaDataParser

    @Before
    fun setUp() {
        parser = MetaDataParser()
    }

    @Test
    fun `parse should correctly map json to SongMetaData`() {
        val json = """
            {
                "title": "Creep",
                "artist": "Radiohead",
                "tracks": [
                { "file": "creep_soprano.mp3", "name": "Soprano" },
                { "file": "creep_alto.mp3", "name": "Alto" },
                { "file": "creep_minus.mp3", "name": "Minus" }
                ]
            }
        """.trimIndent()

        val tempFile = File.createTempFile("test_song", ".json")
        tempFile.writeText(json)

        val result = parser.parse(tempFile)

        assertEquals("Radiohead", result.artist)
        assertEquals("Creep", result.title)
        assertEquals(
            listOf(
                TrackInfo("creep_soprano.mp3", "Soprano"),
                TrackInfo("creep_alto.mp3", "Alto"),
                TrackInfo("creep_minus.mp3", "Minus")
            ),
            result.tracks
        )
        assertEquals("radiohead_creep", result.folderName)

        tempFile.delete()
    }
}
