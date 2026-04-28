package com.obrigada_eu.poika.shared.data.infra.file

import java.io.File

class FileResolver(
    private val appFolder: File
) {

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