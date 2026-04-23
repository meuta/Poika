package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.model.TrackInfo
import com.obrigada_eu.poika.shared.fake.FakeSongRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DeleteSongUseCaseTest {

    private lateinit var repository: FakeSongRepository
    private lateinit var useCase: DeleteSongUseCase

    @Before
    fun setup() {
        repository = FakeSongRepository()
        useCase = DeleteSongUseCase(repository)
    }

    @Test
    fun `should delete folder and return true`() {

        // Given
        val song = someSong()

        repository.shouldDeleteSucceed = true

        // When
        val result = useCase(song)

        // Then
        assertTrue(result)
        assertEquals(song, repository.lastDeletedSong)

    }

    @Test
    fun `should return false if deletion fails`() {
        val song = someSong()

        repository.shouldDeleteSucceed = false
        val result = useCase(song)

        assertFalse(result)
    }

    private fun someSong() =
        SongMetaData("Artist1", "Title1", null, listOf(TrackInfo("123", "456")), "artist1_title1")
}