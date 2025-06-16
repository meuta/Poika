@file:Suppress("IllegalIdentifier")
package com.obrigada_eu.poika.domain.usecase

import android.net.Uri
import com.obrigada_eu.poika.data.ZipImporter
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.domain.TrackInfo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ImportZipUseCaseTest {

    private lateinit var importer: ZipImporter
    private lateinit var useCase: ImportZipUseCase

    @Before
    fun setUp() {
        importer = mock()
        useCase = ImportZipUseCase(importer)
    }

    @Test
    fun `invoke should return SongMetaData from importer`() {
        val uri = mock<Uri>()
        val expected = SongMetaData("U2", "One", "piano", listOf(TrackInfo("123", "456")), "u2_one")

        whenever(importer.importDataFromUri(uri)).thenReturn(expected)

        val result = useCase(uri)

        assertEquals(expected, result)
    }
}

