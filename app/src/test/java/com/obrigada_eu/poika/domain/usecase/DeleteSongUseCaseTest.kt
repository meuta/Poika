package com.obrigada_eu.poika.domain.usecase

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import com.obrigada_eu.poika.player.domain.repository.SongRepository
import com.obrigada_eu.poika.player.domain.usecase.DeleteSongUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DeleteSongUseCaseTest {

    private lateinit var repository: SongRepository
    private lateinit var useCase: DeleteSongUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = DeleteSongUseCase(repository)
    }

    @Test
    fun `should delete folder and return true`() {

        // Given
        val song = someSong()

        whenever(repository.deleteSong(song)).thenReturn(true)

        // When
        val result = useCase(song)

        // Then
        assertTrue(result)
    }

    @Test
    fun `should return false if deletion fails`() {
        val song = someSong()

        whenever(repository.deleteSong(song)).thenReturn(false)
        val result = useCase(song)

        assertFalse(result)
    }

    private fun someSong() =
        SongMetaData("Artist1", "Title1", null, listOf(TrackInfo("123", "456")), "artist1_title1")

}