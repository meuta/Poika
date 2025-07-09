package com.obrigada_eu.poika.player.ui.model

import com.obrigada_eu.poika.player.domain.model.SongMetaData

sealed class UiEvent {

    data class ShowToast(val message: String) : UiEvent()

    data class ShowSongDialog(
        val songs: List<SongMetaData>,
        val mode: Mode
    ) : UiEvent()

    object ShowHelpDialog : UiEvent()

    enum class Mode {
        CHOOSE, DELETE
    }
}