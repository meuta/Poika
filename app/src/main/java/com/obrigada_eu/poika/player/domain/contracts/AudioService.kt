package com.obrigada_eu.poika.player.domain.contracts

import com.obrigada_eu.poika.player.domain.model.SongMetaData

interface AudioService {
    fun loadTracks(songMetaData: SongMetaData)
    fun togglePlayPause()
    fun stop()
    fun setVolume(part: String, volume: Float)
    fun seekTo(positionMs: Long)
    fun rewind(durationMs: Long)
}