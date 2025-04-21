package com.obrigada_eu.poika.domain.usecase

import android.content.Context
import android.net.Uri
import com.obrigada_eu.poika.data.ZipImporter
import com.obrigada_eu.poika.domain.SongMetaData

class ImportZipUseCase(private val context: Context) {

    private val zipImporter = ZipImporter(context)
//
//    operator fun invoke(uri: Uri): Result<Unit> {
//    operator fun invoke(uri: Uri): SongMetaData {
    operator fun invoke(uri: Uri): SongMetaData? {
        return zipImporter.importDataFromUri(uri)
    }

//    fun importZip(uri: Uri): SongMetaData? {
//        val targetDir = unzipToSongFolder(uri) ?: return null
//        val metaFile = File(targetDir, "metadata.json")
//        if (!metaFile.exists()) return null
//
//        return parseMetadata(metaFile)
//    }
}