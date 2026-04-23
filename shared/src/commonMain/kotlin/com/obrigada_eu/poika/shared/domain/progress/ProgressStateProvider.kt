package com.obrigada_eu.poika.shared.domain.progress


import com.obrigada_eu.poika.shared.domain.model.ProgressState
import kotlinx.coroutines.flow.StateFlow

interface ProgressStateProvider {

    fun <T> mapState(transform: (ProgressState) -> T): StateFlow<T>
}