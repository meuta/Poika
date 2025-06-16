package com.obrigada_eu.poika.data

import android.content.Context
import android.net.Uri
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.utils.Logger
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ZipImporter(
    private val context: Context,
    private val metadataParser: MetaDataParser
) {


    fun importDataFromUri(uri: Uri): SongMetaData? {
        val tempDir = unzipToTemporaryFolder(uri) ?: return null
        val metaFile = File(tempDir, "metadata.json")
        if (!metaFile.exists()) {
            Logger.e("FileOps", "metadata.json not found in archive")
            tempDir.deleteRecursively()
            return null
        }

        return try {
            val songMetaData = metadataParser.parse(metaFile)
            val folderName = songMetaData.folderName
            val targetDir = File(context.filesDir, "songs/$folderName")

            copyFiles(tempDir, targetDir)
            songMetaData
        } catch (e: Exception) {
            Logger.e("FileOps", "Error during import", e)
            null
        } finally {
            tempDir.deleteRecursively()
        }
    }


    private fun unzipToTemporaryFolder(uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val zipInputStream = ZipInputStream(inputStream)

        val outputDir = File(context.filesDir, "temp_import")
        outputDir.mkdirs()

        zipInputStream.use { zis ->
            var entry: ZipEntry? = zis.nextEntry
            while (entry != null) {
                val outFile = File(outputDir, entry.name)
                FileOutputStream(outFile).use { fos ->
                    zis.copyTo(fos)
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }

        return outputDir
    }

    private fun copyFiles(from: File, to: File) {
        if (!from.exists()) throw IllegalArgumentException("Source folder does not exist: ${from.absolutePath}")
        if (!from.isDirectory) throw IllegalArgumentException("Source is not a directory: ${from.absolutePath}")
        if (!to.exists()) to.mkdirs()

        from.listFiles()?.forEach { file ->
            val target = File(to, file.name)
            if (file.isDirectory) {
                copyFiles(file, target)
            } else {
                file.copyTo(target, overwrite = true)
            }
        }
    }

}