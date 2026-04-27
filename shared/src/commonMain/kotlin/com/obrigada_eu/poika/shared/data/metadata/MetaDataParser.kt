package com.obrigada_eu.poika.shared.data.metadata

import com.google.gson.Gson
import com.obrigada_eu.poika.shared.domain.metadata.FolderNameGenerator
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import java.io.File

class MetaDataParser {

    fun parse(file: File): SongMetaData {
        // TODO KMP: replace File with platform file abstraction or parse(String)
        val json = file.readText()
        val metaData = Gson().fromJson(json, SongMetaData::class.java)
        val folderName = FolderNameGenerator.from(metaData)
        return metaData.copy(folderName = folderName)
    }
}