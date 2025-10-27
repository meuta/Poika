package com.obrigada_eu.poika.domain.usecase

import android.content.Context
import com.obrigada_eu.poika.data.DeletableFile
import com.obrigada_eu.poika.data.FileResolver
import com.obrigada_eu.poika.domain.SongMetaData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeleteSongUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileResolver: FileResolver
) {
    operator fun invoke(song: SongMetaData): Boolean {

        val folder: DeletableFile =
            fileResolver.resolveDeletable(context.filesDir, "songs/${song.folderName}")
        return folder.deleteRecursively()
    }
}
