package com.obrigada_eu.poika.domain.usecases

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import com.obrigada_eu.poika.player.data.infra.audio.AudioController
import com.obrigada_eu.poika.player.domain.contracts.AudioService
import com.obrigada_eu.poika.player.domain.usecase.LoadSongUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class LoadSongUseCaseTest {

    private lateinit var audioService: AudioService
    private lateinit var useCase: LoadSongUseCase

    @Before
    fun setUp() {
        audioService = mock()
        useCase = LoadSongUseCase(audioService)
    }

    @Test
    fun `invoke should delegate to audioController`() {
        val song = SongMetaData("MIKA", "Relax", "voice", listOf(TrackInfo("123", "456")), "mika_relax")

        useCase(song)

        verify(audioService).loadTracks(song)
    }
}
