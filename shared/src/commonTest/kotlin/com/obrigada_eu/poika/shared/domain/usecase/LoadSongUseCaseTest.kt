package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.model.TrackInfo
import com.obrigada_eu.poika.shared.fake.FakeAudioController
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LoadSongUseCaseTest {

    private lateinit var audioService: FakeAudioController
    private lateinit var useCase: LoadSongUseCase

    @Before
    fun setUp() {
        audioService = FakeAudioController()
        useCase = LoadSongUseCase(audioService)
    }

    @Test
    fun `invoke should delegate to audioController`() {
        val song = SongMetaData("MIKA", "Relax", "voice", listOf(TrackInfo("123", "456")), "mika_relax")

        useCase(song)

        assertEquals(1, audioService.loadTracksCallCount)
        assertEquals(song, audioService.lastLoadedSong)
    }
}