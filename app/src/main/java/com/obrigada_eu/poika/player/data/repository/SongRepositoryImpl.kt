package com.obrigada_eu.poika.player.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.obrigada_eu.poika.player.data.infra.file.FileResolver
import com.obrigada_eu.poika.shared.data.metadata.MetaDataParser
import com.obrigada_eu.poika.player.data.infra.file.ZipImporter
import com.obrigada_eu.poika.shared.domain.repository.SongRepository
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.utils.Logger
import java.io.File

class SongRepositoryImpl(
    private val context: Context,
    private val zipImporter: ZipImporter,
    private val metaDataParser: MetaDataParser,
) : SongRepository {

    override fun importSong(uriString: String): SongMetaData? {
        return zipImporter.importDataFromUri(uriString.toUri())
    }

    override fun getAllSongsMetadata(): List<SongMetaData> {
        val songsDir = FileResolver(context).getSongsFolder()
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
        val folder = FileResolver(context).getSongFolder(song.folderName)
        return folder.deleteRecursively()
    }
}