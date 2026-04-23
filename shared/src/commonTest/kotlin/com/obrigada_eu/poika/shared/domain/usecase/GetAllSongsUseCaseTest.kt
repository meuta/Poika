package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.model.TrackInfo
import com.obrigada_eu.poika.shared.fake.FakeSongRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetAllSongsUseCaseTest {

    private lateinit var repository: FakeSongRepository
    private lateinit var useCase: GetAllSongsUseCase

    @Before
    fun setUp() {
        repository = FakeSongRepository()
        useCase = GetAllSongsUseCase(repository)
    }

    @Test
    fun `invoke should return list of SongMetaData from repository`() {
        val expected = listOf(
            SongMetaData("Artist1", "Title1", "piano", listOf(TrackInfo("123", "456")), "artist1_title1"),
            SongMetaData("Artist2", "Title2", "guitar", listOf(TrackInfo("123", "456")), "artist2_title2")
        )

        repository.songs = expected
        val result = useCase.invoke()

        assertEquals(expected, result)
    }
}
