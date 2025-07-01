package com.obrigada_eu.poika


interface PlayerSessionReader {
    fun getCurrentSongTitle(): String?
    fun getVolumeList(): List<Float>
}

interface PlayerSessionWriter {
    fun setCurrentSongTitle(title: String?)
    fun setVolume(trackIndex: Int, volume: Float)
}

object PlayerSession : PlayerSessionReader, PlayerSessionWriter {

    private var _currentSongTitle: String? = null
    private var _volumeList: MutableList<Float> = mutableListOf(1f, 1f, 1f)

    override fun getCurrentSongTitle(): String? = _currentSongTitle

    override fun getVolumeList(): List<Float> = _volumeList.toList()

    override fun setCurrentSongTitle(title: String?) {
        _currentSongTitle = title
    }

    override fun setVolume(trackIndex: Int, volume: Float) {
        if (trackIndex in _volumeList.indices) {
            _volumeList[trackIndex] = volume
        }
    }
}