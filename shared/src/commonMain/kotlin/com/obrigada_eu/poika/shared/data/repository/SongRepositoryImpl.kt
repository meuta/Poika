package com.obrigada_eu.poika.shared.data.repository


import com.obrigada_eu.poika.shared.data.metadata.MetaDataParser
import com.obrigada_eu.poika.shared.data.infra.file.ZipImporter
import com.obrigada_eu.poika.shared.Logger
import com.obrigada_eu.poika.shared.data.infra.file.FileResolver
import com.obrigada_eu.poika.shared.data.infra.file.SongInputStreamProvider
import com.obrigada_eu.poika.shared.domain.repository.SongRepository
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import java.io.File

class SongRepositoryImpl(
//    private val context: Context,

    private val streamProvider: SongInputStreamProvider,
    private val zipImporter: ZipImporter,
    private val metaDataParser: MetaDataParser,
    private val fileResolver: FileResolver
) : SongRepository {


    override fun importSong(source: String): SongMetaData? {
//        val inputStream = context.contentResolver.openInputStream(source.toUri())
        val stream = streamProvider.open(source)
//        return inputStream?.let { stream -> zipImporter.import(stream) }
        return stream?.let { zipImporter.import(it) }
    }

    override fun getAllSongsMetadata(): List<SongMetaData> {
        val songsDir = fileResolver.getSongsFolder()
        if (!songsDir.exists()) return emptyList()

        return songsDir.listFiles()
            ?.mapNotNull { folder ->
                val metaFile = File(folder, "metadata.json")
                if (metaFile.exists()) {
                    try {
                        metaDataParser.parse(metaFile)
                    } catch (e: Exception) {
                        Logger.e("Repository", "Can't parse ${folder.name}", e)
                        null
                    }
                } else null
            } ?: emptyList()
    }

    override fun deleteSong(song: SongMetaData): Boolean {
        val folder = fileResolver.getSongFolder(song.folderName)
        return folder.deleteRecursively()
    }
}