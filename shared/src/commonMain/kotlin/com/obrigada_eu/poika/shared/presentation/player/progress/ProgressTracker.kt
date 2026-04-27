package com.obrigada_eu.poika.shared.presentation.player.progress

import com.obrigada_eu.poika.shared.domain.model.ProgressState
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressTracker(private val scope: CoroutineScope) : ProgressStateUpdater, ProgressStateProvider {

    private val progressStateFlow: MutableStateFlow<ProgressState> = MutableStateFlow(ProgressState())

    override fun update(value: ProgressState) {
        progressStateFlow.value = value
    }

    override fun currentState(): ProgressState = progressStateFlow.value

    override fun <T> mapState(transform: (ProgressState) -> T): StateFlow<T> {
        return progressStateFlow
            .map(transform)
            .stateIn(scope, SharingStarted.Lazily, transform(ProgressState()))
    }
}