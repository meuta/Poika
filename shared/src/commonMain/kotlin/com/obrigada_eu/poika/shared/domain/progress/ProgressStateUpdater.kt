package com.obrigada_eu.poika.shared.domain.progress

import com.obrigada_eu.poika.shared.domain.model.ProgressState


interface ProgressStateUpdater {
    fun update(value: ProgressState)
    fun currentState(): ProgressState
}