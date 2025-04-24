package com.obrigada_eu.poika.domain.usecase

import android.content.Context
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.domain.TrackInfo
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

class DeleteSongUseCaseTest {

    private lateinit var context: Context
    private lateinit var useCase: DeleteSongUseCase

    @Before
    fun setup() {
        context = mock()
        useCase = DeleteSongUseCase(context)
    }

    @Test
    fun `should delete folder and return true`() {
        // given
        val song = SongMetaData("Artist1", "Title1", listOf(TrackInfo("123", "456")), "artist1_title1")

        val filesDir = mock<File>()
        val targetFolder = mock<File>()

        whenever(context.filesDir).thenReturn(filesDir)
        whenever(filesDir.resolve("songs/${song.folderName}")).thenReturn(targetFolder)
        whenever(targetFolder.deleteRecursively()).thenReturn(true)

        // when
        val result = useCase(song)

        // then
        assertTrue(result)
        verify(targetFolder).deleteRecursively()
    }

    @Test
    fun `should return false if deletion fails`() {
        val song = SongMetaData(folderName = "bad_folder", title = "", artist = "", tracks = listOf())
        val filesDir = mock<File>()
        val targetFolder = mock<File>()

        whenever(context.filesDir).thenReturn(filesDir)
        whenever(filesDir.resolve("songs/${song.folderName}")).thenReturn(targetFolder)
        whenever(targetFolder.deleteRecursively()).thenReturn(false)

        val result = useCase(song)

        assertFalse(result)
    }
}
