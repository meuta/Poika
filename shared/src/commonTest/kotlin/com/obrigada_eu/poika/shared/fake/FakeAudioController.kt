package com.obrigada_eu.poika.shared.fake

import com.obrigada_eu.poika.shared.domain.audio.AudioService
import com.obrigada_eu.poika.shared.domain.model.SongMetaData

class FakeAudioController : AudioService {

    var lastLoadedSong: SongMetaData? = null
    var loadTracksCallCount = 0

    override fun loadTracks(songMetaData: SongMetaData) {
        lastLoadedSong = songMetaData
        loadTracksCallCount++
    }

    override fun togglePlayPause() {}

    override fun stop() {}

    override fun setVolume(part: String, volume: Float) {}

    override fun seekTo(positionMs: Long) {}

    override fun rewind(durationMs: Long) {}

    override fun changeSpeed(speedDif: Float) {}
}