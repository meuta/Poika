package com.obrigada_eu.poika.player.data.infra.session

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerSession(private val scope: CoroutineScope) : PlayerSessionWriter, PlayerSessionReader {

    private val currentSong = MutableStateFlow<SongMetaData?>(null)

    private val volumeLevels = MutableStateFlow<Map<String, Float>>(mutableMapOf())

    private val isPlaying = MutableStateFlow(false)

    override fun setCurrentSong(metaData: SongMetaData?) {
        currentSong.value = metaData
    }

    override fun setTrackVolume(part: String, volume: Float) {
        val updatedMap = volumeLevels.value.toMutableMap()
        updatedMap[part] = volume
        volumeLevels.value = updatedMap
    }

    override fun setIsPlaying(isPlaying: Boolean) {
        this.isPlaying.value = isPlaying
    }

    override fun updateParts(parts: Map<String, Float>) {
        volumeLevels.value = parts
    }

    override fun <T> currentSongFlow(transform: (SongMetaData) -> T?): StateFlow<T?> {
        return currentSong
            .map { data -> data?.let(transform) }
            .stateIn(scope, SharingStarted.Lazily, null)
    }

    override fun volumeLevelsFlow(): StateFlow<Map<String, Float>> = volumeLevels

    override fun isPlayingFlow(): StateFlow<Boolean> = isPlaying

    companion object {
        private const val TAG = "PlayerSession"
    }
}