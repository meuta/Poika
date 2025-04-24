package com.obrigada_eu.poika.ui

import com.obrigada_eu.poika.domain.SongMetaData

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class ShowSongDialog(
        val songs: List<SongMetaData>,
        val mode: Mode
    ) : UiEvent()

    enum class Mode {
        CHOOSE, DELETE
    }
}