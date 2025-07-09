package com.obrigada_eu.poika.domain.usecases

import android.content.Context
import com.obrigada_eu.poika.player.domain.contracts.DeletableFile
import com.obrigada_eu.poika.player.data.infra.file.FileResolver
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import com.obrigada_eu.poika.player.domain.usecase.DeleteSongUseCase
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

class DeleteSongUseCaseTest {

    private lateinit var context: Context
    private lateinit var fileResolver: FileResolver
    private lateinit var useCase: DeleteSongUseCase

    @Before
    fun setup() {
        context = mock()
        fileResolver = mock()
        useCase = DeleteSongUseCase(context, fileResolver)
    }

    @Test
    fun `should delete folder and return true`() {

        // Given
        val song = someSong()
        val filesDir = mock<File>()
        val targetFolder = mock<DeletableFile>()

        whenever(context.filesDir).thenReturn(filesDir)
        whenever(fileResolver.resolveDeletable(filesDir, "songs/${song.folderName}")).thenReturn(targetFolder)
        whenever(targetFolder.deleteRecursively()).thenReturn(true)

        // When
        val result = useCase(song)

        // Then
        assertTrue(result)
        verify(targetFolder).deleteRecursively()
    }

    @Test
    fun `should return false if deletion fails`() {
        val song = someSong()
        val filesDir = mock<File>()
        val targetFolder = mock<DeletableFile>()

        whenever(context.filesDir).thenReturn(filesDir)
        whenever(fileResolver.resolveDeletable(any(), any())).thenReturn(targetFolder)
        whenever(targetFolder.deleteRecursively()).thenReturn(false)

        val result = useCase(song)

        assertFalse(result)
    }

    private fun someSong() = SongMetaData("Artist1", "Title1", null, listOf(TrackInfo("123", "456")), "artist1_title1")

}
