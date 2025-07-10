package com.obrigada_eu.poika.player.domain.progress


import kotlinx.coroutines.flow.StateFlow

interface ProgressStateProvider {

    fun <T> getState(transform: (ProgressState) -> T): StateFlow<T>
}