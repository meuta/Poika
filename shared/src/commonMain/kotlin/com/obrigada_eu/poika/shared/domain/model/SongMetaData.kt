package com.obrigada_eu.poika.shared.domain.model

import com.google.gson.annotations.SerializedName

data class SongMetaData(
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("voiceInstrument") val voiceInstrument: String?,
    @SerializedName("tracks") val tracks: List<TrackInfo>,
    val folderName: String = ""
)

data class TrackInfo(
    @SerializedName("file") val file: String,
    @SerializedName("name") val name: String
)