package com.obrigada_eu.poika.player.domain.session

interface PlayerSessionReader {
    fun getCurrentSongTitle(): String?
    fun getVolumeList(): List<Float>
}