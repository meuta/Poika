package com.obrigada_eu.poika.shared.presentation.player.mappers

import com.obrigada_eu.poika.shared.domain.model.ProgressState
import com.obrigada_eu.poika.shared.presentation.player.formatters.TimeStringFormatter
import com.obrigada_eu.poika.shared.presentation.player.model.ProgressStateUi

fun ProgressState.toUi(stringFormatter: TimeStringFormatter): ProgressStateUi {
    val currentPositionSec = currentPosition / 1000f
    val currentPositionString = stringFormatter.formatMillisToString(currentPosition)
    val durationSec = duration / 1000f
    val durationString = stringFormatter.formatMillisToString(duration)

    return ProgressStateUi(
        currentPositionSec,
        currentPositionString,
        durationSec,
        durationString
    )
}