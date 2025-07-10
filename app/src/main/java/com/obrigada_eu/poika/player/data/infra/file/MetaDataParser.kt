package com.obrigada_eu.poika.player.data.infra.file

import com.google.gson.Gson
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import java.io.File
import java.text.Normalizer
import java.util.Locale
import kotlin.text.iterator


class MetaDataParser {

    fun parse(file: File): SongMetaData {
        val json = file.readText()
        val metaData = Gson().fromJson(json, SongMetaData::class.java)
        val folderName = FolderNameGenerator.from(metaData)
        return metaData.copy(folderName = folderName)
    }
}


object FolderNameGenerator {
    fun from(metaData: SongMetaData): String {
        val artist = toSafeName(metaData.artist, 25)
        val title = toSafeName(metaData.title, 30)
        val voiceInstrument = metaData.voiceInstrument?.let {
            toSafeName(it, 17).plus("_version")
        }
        val raw = listOfNotNull(artist, title, voiceInstrument).joinToString("__")
        return raw
    }

    private fun toSafeName(input: String, maxLength: Int): String {
        val transliterated = transliterate(input.lowercase(Locale.getDefault()))

        return Normalizer.normalize(transliterated, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "") // removal of accents
            .replace("[^a-z0-9]+".toRegex(), "_") //what is not a-z and 0-9 - replace with "_"
            .replace(Regex("_+"), "_") // collapse multiple underscores
            .trim('_')
            .take(maxLength)
    }

    private fun transliterate(input: String): String {
        val map = mapOf(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
            'е' to "e", 'ё' to "e", 'ж' to "zh", 'з' to "z", 'и' to "i",
            'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
            'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
            'у' to "u", 'ф' to "f", 'х' to "h", 'ц' to "ts", 'ч' to "ch",
            'ш' to "sh", 'щ' to "sch", 'ъ' to "", 'ы' to "y", 'ь' to "",
            'э' to "e", 'ю' to "yu", 'я' to "ya"
        )
        return buildString {
            for (char in input) {
                append(map[char] ?: char)
            }
        }
    }
}



