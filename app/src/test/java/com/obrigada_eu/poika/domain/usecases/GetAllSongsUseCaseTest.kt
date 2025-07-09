package com.obrigada_eu.poika.domain.usecases

import com.obrigada_eu.poika.player.domain.repository.SongRepository
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import com.obrigada_eu.poika.player.domain.usecase.GetAllSongsUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllSongsUseCaseTest {

    private lateinit var repository: SongRepository
    private lateinit var useCase: GetAllSongsUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetAllSongsUseCase(repository)
    }

    @Test
    fun `invoke should return list of SongMetaData from repository`() {
        val expected = listOf(
            SongMetaData("Artist1", "Title1", "piano", listOf(TrackInfo("123", "456")), "artist1_title1"),
            SongMetaData("Artist2", "Title2", "guitar", listOf(TrackInfo("123", "456")), "artist2_title2")
        )

        whenever(repository.getAllSongsMetadata()).thenReturn(expected)

        val result = useCase.invoke()

        assertEquals(expected, result)
    }
}
