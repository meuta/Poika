package com.obrigada_eu.poika.player.data.infra.session

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter

object PlayerSession : PlayerSessionReader, PlayerSessionWriter {

    private var songMetaData: SongMetaData? = null
    private var volumeList: MutableList<Float> = mutableListOf(1f, 1f, 1f)

    override fun <T> getCurrentSongTitle(transform: (SongMetaData) -> T?): T? {
        return songMetaData?.let { transform(it) }
    }

    override fun getVolumeList(): List<Float> = volumeList.toList()

    override fun setCurrentSong(metaData: SongMetaData?) {
        songMetaData = metaData
    }

    override fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in volumeList.indices) {
            volumeList[trackIndex] = volume
        }
    }
}