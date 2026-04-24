package com.obrigada_eu.poika.shared.presentation.player.model

import com.obrigada_eu.poika.shared.domain.model.SongMetaData

sealed class UiEvent {

    data class ShowToast(val message: List<UiTextPart>, val shortDuration: Boolean) : UiEvent()

    data class ShowSongDialog(
        val songs: List<SongMetaData>,
        val mode: Mode
    ) : UiEvent()

    object ShowHelpDialog : UiEvent()

    enum class Mode {
        CHOOSE, DELETE
    }
}