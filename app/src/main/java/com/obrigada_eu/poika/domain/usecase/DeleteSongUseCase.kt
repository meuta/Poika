package com.obrigada_eu.poika.domain.usecase

import android.content.Context
import com.obrigada_eu.poika.domain.SongMetaData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DeleteSongUseCase @Inject constructor(@ApplicationContext private val context: Context) {

    operator fun invoke(song: SongMetaData): Boolean {
        val folder = File(context.filesDir, "songs/${song.folderName}")
        return folder.deleteRecursively()
    }
}