package com.obrigada_eu.poika.player.domain.session

interface PlayerSessionWriter {
    fun setCurrentSongTitle(title: String?)
    fun setVolume(trackIndex: Int, volume: Float)
}