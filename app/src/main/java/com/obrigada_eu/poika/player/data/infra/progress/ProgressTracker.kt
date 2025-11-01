package com.obrigada_eu.poika.player.data.infra.progress

import com.obrigada_eu.poika.player.domain.model.ProgressState
import com.obrigada_eu.poika.player.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.player.domain.progress.ProgressStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressTracker(private val scope: CoroutineScope) : ProgressStateUpdater, ProgressStateProvider {

    private val stateFlow: MutableStateFlow<ProgressState> = MutableStateFlow(ProgressState())

    override fun update(value: ProgressState) {
        stateFlow.value = value
    }

    override fun value(): ProgressState = stateFlow.value

    override fun <T> getState(transform: (ProgressState) -> T): StateFlow<T> {
        return stateFlow
            .map(transform)
            .stateIn(scope, SharingStarted.Companion.Lazily, transform(ProgressState()))
    }
}