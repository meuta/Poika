package com.obrigada_eu.poika.data

import com.obrigada_eu.poika.player.data.infra.file.FolderNameGenerator
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import org.junit.Test
import org.junit.Assert.*

class FolderNameGeneratorTest {

    @Test
    fun `from should generate folder name from metadata`() {
        val meta = SongMetaData(
            artist = "Moby",
            title = "Natural Blues",
            voiceInstrument = "piano",
            tracks = listOf(TrackInfo("123", "456")),
            folderName = "567"
        )
        val result = FolderNameGenerator.from(meta)
        assertEquals("moby__natural_blues__piano_version", result)
    }


    @Test
    fun `from should remove non-alphanumeric characters`() {
        val meta = SongMetaData(
            artist = "Daft Punk",
            title = "Harder, Better, Faster, Stronger",
            voiceInstrument = "synth",
            tracks = listOf(TrackInfo("", "")),
            folderName = ""
        )
        val result = FolderNameGenerator.from(meta)
        assertEquals("daft_punk__harder_better_faster_stronger__synth_version", result)
    }

    @Test
    fun `from should remove accents`() {
        val meta = SongMetaData(
            artist = "Amália Rodrigues",
            title = "Uma Casa Portuguesa",
            voiceInstrument = null,
            tracks = listOf(TrackInfo("", "")),
            folderName = ""
        )
        val result = FolderNameGenerator.from(meta)
        assertEquals("amalia_rodrigues__uma_casa_portuguesa", result)
    }
}