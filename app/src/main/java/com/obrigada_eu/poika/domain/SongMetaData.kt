package com.obrigada_eu.poika.domain

data class SongMetaData(
    val title: String,
    val artist: String,
    val tracks: List<TrackInfo>
)

data class TrackInfo(
    val file: String,
    val name: String
)