package com.obrigada_eu.poika.shared.data.infra.file

import com.obrigada_eu.poika.shared.Logger
import com.obrigada_eu.poika.shared.data.metadata.MetaDataParser
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.collections.forEach

class ZipImporter(
    private val fileResolver: FileResolver,
    private val metadataParser: MetaDataParser
) {

    fun import(inputStream: InputStream): SongMetaData? {
        val tempDir = extractToTemporaryFolder(inputStream) ?: return null

        val metaFile = File(tempDir, "metadata.json")
        if (!metaFile.exists()) {
            Logger.e("FileOps", "metadata.json not found in archive")
            tempDir.deleteRecursively()
            return null
        }

        return try {
            val songMetaData = metadataParser.parse(metaFile)
            val targetDir = fileResolver.getSongFolder(songMetaData.folderName)

            copyFiles(tempDir, targetDir)
            songMetaData
        } catch (e: Exception) {
            Logger.e("FileOps", "Error during import", e)
            null
        } finally {
            tempDir.deleteRecursively()
        }
    }
    private fun extractToTemporaryFolder(inputStream: InputStream): File? {
        val zipInputStream = ZipInputStream(inputStream)

        val tempDir = fileResolver.getTempImportFolder()
        tempDir.mkdirs()

        zipInputStream.use { zis ->
            var entry: ZipEntry? = zis.nextEntry
            while (entry != null) {
                val outFile = File(tempDir, entry.name)
                FileOutputStream(outFile).use { fos ->
                    zis.copyTo(fos)
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }
        return tempDir
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