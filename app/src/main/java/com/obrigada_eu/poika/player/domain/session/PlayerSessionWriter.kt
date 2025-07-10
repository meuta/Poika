package com.obrigada_eu.poika.player.domain.session

import com.obrigada_eu.poika.player.domain.model.SongMetaData

interface PlayerSessionWriter {
    fun setCurrentSong(metaData: SongMetaData?)
    fun setVolume(trackIndex: Int, volume: Float)
}