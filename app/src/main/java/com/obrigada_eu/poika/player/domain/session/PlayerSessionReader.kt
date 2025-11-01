package com.obrigada_eu.poika.player.domain.session

import com.obrigada_eu.poika.player.domain.model.SongMetaData

interface PlayerSessionReader {
    fun <T> getCurrentSongTitle(transform: (SongMetaData) -> T?): T?
    fun getVolumeList(): List<Float>
}