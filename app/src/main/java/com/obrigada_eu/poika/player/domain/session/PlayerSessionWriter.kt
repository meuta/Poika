package com.obrigada_eu.poika.player.domain.session

import com.obrigada_eu.poika.player.domain.model.SongMetaData

interface PlayerSessionWriter {
    fun setCurrentSong(metaData: SongMetaData?)
    fun setTrackVolume(part: String, volume: Float)
    fun setIsPlaying(isPlaying: Boolean)
    fun setSpeed(speed: Float)
    fun updateParts(parts: Map<String, Float>)
}