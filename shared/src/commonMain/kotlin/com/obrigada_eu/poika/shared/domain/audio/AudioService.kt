package com.obrigada_eu.poika.shared.domain.audio

import com.obrigada_eu.poika.shared.domain.model.SongMetaData

interface AudioService {
    fun loadTracks(songMetaData: SongMetaData)
    fun togglePlayPause()
    fun stop()
    fun setVolume(part: String, volume: Float)
    fun seekTo(positionMs: Long)
    fun rewind(durationMs: Long)
    fun changeSpeed(speedDif: Float)
}