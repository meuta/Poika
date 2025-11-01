package com.obrigada_eu.poika.player.domain.session

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import kotlinx.coroutines.flow.StateFlow

interface PlayerSessionReader {
    fun <T> currentSongFlow(transform: (SongMetaData) -> T?): StateFlow<T?>
    fun volumeLevelsFlow(): StateFlow<List<Float>>
}