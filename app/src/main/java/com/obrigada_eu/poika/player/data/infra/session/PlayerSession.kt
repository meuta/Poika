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
import kotlinx.coroutines.flow.update

class PlayerSession(private val scope: CoroutineScope) : PlayerSessionWriter, PlayerSessionReader {

    private val currentSong = MutableStateFlow<SongMetaData?>(null)
    private val defaultVolumes = listOf(1f, 1f, 1f)
    private val volumeLevels = MutableStateFlow(defaultVolumes)

    private val isPlaying = MutableStateFlow(false)

    override fun setCurrentSong(metaData: SongMetaData?) {
        currentSong.value = metaData
    }

    override fun setTrackVolume(trackIndex: Int, volume: Float) {
        volumeLevels.update { list ->
            list.mapIndexed { index, current ->
                if (index == trackIndex) volume else current
            }
        }
    }

    override fun setIsPlaying(isPlaying: Boolean) {
        this.isPlaying.value = isPlaying
    }

    override fun <T> currentSongFlow(transform: (SongMetaData) -> T?): StateFlow<T?> {
        return currentSong
            .map { data -> data?.let(transform) }
            .stateIn(scope, SharingStarted.Lazily, null)
    }

    override fun volumeLevelsFlow(): StateFlow<List<Float>> = volumeLevels

    override fun isPlayingFlow(): StateFlow<Boolean> = isPlaying
}