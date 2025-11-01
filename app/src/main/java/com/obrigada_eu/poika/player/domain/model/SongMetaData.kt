package com.obrigada_eu.poika.player.domain.model

data class SongMetaData(
    val title: String,
    val artist: String,
    val voiceInstrument: String?,
    val tracks: List<TrackInfo>,
    val folderName: String
)

data class TrackInfo(
    val file: String,
    val name: String
)