package com.obrigada_eu.poika.player.data.infra.file

import android.content.Context
import java.io.File

class FileResolver(
    context: Context,
) {
    val appFolder: File? = context.filesDir

    fun getSongsFolder(): File {
        return File(appFolder, "songs")
    }
    fun getSongFolder(folderName: String): File {
        return File(getSongsFolder(), folderName)
    }

    fun getTempImportFolder(): File {
        return File(appFolder, "temp_import")
    }
}