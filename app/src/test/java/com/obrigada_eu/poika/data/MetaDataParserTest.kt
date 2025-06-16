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
    fun `parse should correctly map json without voiceInstrument to SongMetaData`() {
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
        assertEquals("radiohead__creep", result.folderName)

        tempFile.delete()
    }

    @Test
    fun `parse should correctly map json with voiceInstrument to SongMetaData`() {
        val json = """
            {
                "title": "Uprising",
                "artist": "Muse",
                "voiceInstrument": "piano",
                "tracks": [
                { "file": "uprising_soprano.mp3", "name": "Soprano" },
                { "file": "uprising_alto.mp3", "name": "Alto" },
                { "file": "uprising_minus.mp3", "name": "Minus" }
                ]
            }
        """.trimIndent()

        val tempFile = File.createTempFile("test_song", ".json")
        tempFile.writeText(json)

        val result = parser.parse(tempFile)

        assertEquals("Muse", result.artist)
        assertEquals("Uprising", result.title)
        assertEquals("piano", result.voiceInstrument)
        assertEquals(
            listOf(
                TrackInfo("uprising_soprano.mp3", "Soprano"),
                TrackInfo("uprising_alto.mp3", "Alto"),
                TrackInfo("uprising_minus.mp3", "Minus")
            ),
            result.tracks
        )
        assertEquals("muse__uprising__piano_version", result.folderName)

        tempFile.delete()
    }
}
