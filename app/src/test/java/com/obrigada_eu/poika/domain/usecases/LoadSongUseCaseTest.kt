package com.obrigada_eu.poika.domain.usecases

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import com.obrigada_eu.poika.player.data.infra.audio.AudioController
import com.obrigada_eu.poika.player.domain.usecase.LoadSongUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class LoadSongUseCaseTest {

    private lateinit var audioController: AudioController
    private lateinit var useCase: LoadSongUseCase

    @Before
    fun setUp() {
        audioController = mock()
        useCase = LoadSongUseCase(audioController)
    }

    @Test
    fun `invoke should delegate to audioController`() {
        val song = SongMetaData("MIKA", "Relax", "voice", listOf(TrackInfo("123", "456")), "mika_relax")

        useCase(song)

        verify(audioController).loadTracks(song)
    }
}
