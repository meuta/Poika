package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.model.TrackInfo
import com.obrigada_eu.poika.shared.fake.FakeSongRepository
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ImportZipUseCaseTest {

    private lateinit var repository: FakeSongRepository
    private lateinit var useCase: ImportZipUseCase

    @Before
    fun setUp() {
        repository = FakeSongRepository()
        useCase = ImportZipUseCase(repository)
    }

    @Test
    fun `invoke should return SongMetaData from importer`() {
        val uriString = "content://test/song.zip"
        val expected = SongMetaData("U2", "One", "piano", listOf(TrackInfo("123", "456")), "u2_one")

        repository.songToReturn = expected
        val result = useCase(uriString)

        assertEquals(expected, result)
        assertEquals(uriString, repository.lastImportedUri)
    }
}