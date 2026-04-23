package com.obrigada_eu.poika.shared.domain.session

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import kotlinx.coroutines.flow.StateFlow

interface PlayerSessionReader {
    fun <T> currentSongFlow(transform: (SongMetaData) -> T?): StateFlow<T?>
    fun volumeLevelsFlow(): StateFlow<Map<String, Float>>
    fun isPlayingFlow(): StateFlow<Boolean>
    fun currentSpeedFlow(): StateFlow<Float>
    fun <T> mapSpeed(transform: (Float) -> T): StateFlow<T>
}