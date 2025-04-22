package com.obrigada_eu.poika.data

import android.content.Context
import android.util.Log
import com.obrigada_eu.poika.domain.SongMetaData
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
                        Log.e("Repository", "Can't parse ${folder.name}", e)
                        null
                    }
                } else null
            } ?: emptyList()
    }
}
