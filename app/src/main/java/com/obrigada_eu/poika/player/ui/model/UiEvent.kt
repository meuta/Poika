package com.obrigada_eu.poika.player.ui.model

import android.text.Spannable
import com.obrigada_eu.poika.shared.domain.model.SongMetaData

sealed class UiEvent {

    data class ShowToast(val message: Spannable, val shortDuration: Boolean) : UiEvent()

    data class ShowSongDialog(
        val songs: List<SongMetaData>,
        val mode: Mode
    ) : UiEvent()

    object ShowHelpDialog : UiEvent()

    enum class Mode {
        CHOOSE, DELETE
    }
}