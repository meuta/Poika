package com.obrigada_eu.poika.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.model.TrackInfo
import com.obrigada_eu.poika.shared.domain.repository.SongRepository
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ImportZipUseCaseTest {

    private lateinit var repository: SongRepository
    private lateinit var useCase: ImportZipUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = ImportZipUseCase(repository)
    }

    @Test
    fun `invoke should return SongMetaData from importer`() {
        val uri = "content://test/song.zip"
        val expected = SongMetaData("U2", "One", "piano", listOf(TrackInfo("123", "456")), "u2_one")

        whenever(repository.importSong(uri)).thenReturn(expected)

        val result = useCase(uri)

        assertEquals(expected, result)
    }
}