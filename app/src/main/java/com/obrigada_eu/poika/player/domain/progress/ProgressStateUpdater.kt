package com.obrigada_eu.poika.player.domain.progress


interface ProgressStateUpdater {
    fun update(value: ProgressState)
    fun value(): ProgressState
}