package com.obrigada_eu.poika.player.data.infra.audio

import com.obrigada_eu.poika.common.formatters.StringFormatter
import com.obrigada_eu.poika.player.domain.model.ProgressState
import com.obrigada_eu.poika.player.ui.mapper.toUi
import com.obrigada_eu.poika.player.ui.model.ProgressStateUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressTracker {

    private val stateFlow: MutableStateFlow<ProgressState> = MutableStateFlow(ProgressState())

    private val scope = CoroutineScope(Dispatchers.Main)

    fun update(value: ProgressState){ stateFlow.value = value }

    fun map(stringFormatter: StringFormatter): StateFlow<ProgressStateUi> {
        return stateFlow
            .map { it.toUi(stringFormatter) }
            .stateIn(scope, SharingStarted.Companion.Lazily, ProgressState().toUi(stringFormatter))
    }

    fun value() = stateFlow.value

}