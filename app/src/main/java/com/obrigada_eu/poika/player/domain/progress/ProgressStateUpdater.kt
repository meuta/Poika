package com.obrigada_eu.poika.player.domain.progress

import com.obrigada_eu.poika.player.domain.model.ProgressState


interface ProgressStateUpdater {
    fun update(value: ProgressState)
    fun value(): ProgressState
}