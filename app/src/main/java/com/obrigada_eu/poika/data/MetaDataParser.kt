package com.obrigada_eu.poika.data

import com.google.gson.Gson
import com.obrigada_eu.poika.domain.SongMetaData
import java.io.File

class MetaDataParser {

    fun parse(file: File): SongMetaData {
        val json = file.readText()
        val gson = Gson()
        return gson.fromJson(json, SongMetaData::class.java)
    }
}