@file:Suppress("IllegalIdentifier")

package com.obrigada_eu.poika.domain.usecase

import com.obrigada_eu.poika.data.SongRepository
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.domain.TrackInfo
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
            SongMetaData("Artist1", "Title1", listOf(TrackInfo("123", "456")), "artist1_title1"),
            SongMetaData("Artist2", "Title2", listOf(TrackInfo("123", "456")), "artist2_title2")
        )

        whenever(repository.getAllSongsMetadata()).thenReturn(expected)

        val result = useCase.invoke()

        assertEquals(expected, result)
    }
}
