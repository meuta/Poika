package com.obrigada_eu.poika.data

import android.content.Context
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.utils.Logger
import java.io.File

class SongRepositoryImpl(
    private val context: Context,
    private val metadataParser: MetaDataParser
) : SongRepository {

    override fun getAllSongsMetadata(): List<SongMetaData> {
        val songsDir = File(context.filesDir, "songs")
        if (!songsDir.exists()) return emptyList()

        return songsDir.listFiles()
            ?.mapNotNull { folder ->
                val metaFile = File(folder, "metadata.json")
                if (metaFile.exists()) {
                    try {
                        metadataParser.parse(metaFile)
                    } catch (e: Exception) {
                        Logger.e("Repository", "Can't parse ${folder.name}", e)
                        null
                    }
                } else null
            } ?: emptyList()
    }
}
