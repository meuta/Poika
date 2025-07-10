package com.obrigada_eu.poika.player.data.infra.session

import com.obrigada_eu.poika.player.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter

object PlayerSession : PlayerSessionReader, PlayerSessionWriter {

    private var currentSongTitle: String? = null
    private var volumeList: MutableList<Float> = mutableListOf(1f, 1f, 1f)

    override fun getCurrentSongTitle(): String? = currentSongTitle

    override fun getVolumeList(): List<Float> = volumeList.toList()

    override fun setCurrentSongTitle(title: String?) {
        currentSongTitle = title
    }

    override fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in volumeList.indices) {
            volumeList[trackIndex] = volume
        }
    }
}