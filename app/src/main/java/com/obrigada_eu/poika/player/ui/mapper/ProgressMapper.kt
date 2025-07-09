package com.obrigada_eu.poika.player.ui.mapper

import com.obrigada_eu.poika.common.formatters.StringFormatter
import com.obrigada_eu.poika.player.domain.model.ProgressState
import com.obrigada_eu.poika.player.ui.model.ProgressStateUi

fun ProgressState.toUi(stringFormatter: StringFormatter): ProgressStateUi {
    val currentPositionSec = currentPosition / 1000
    val currentPositionString = stringFormatter.formatMillisToString(currentPosition)
    val durationSec = duration / 1000
    val durationString = stringFormatter.formatMillisToString(duration)

    return ProgressStateUi(
        currentPositionSec,
        currentPositionString,
        isFinished,
        durationSec,
        durationString
    )
}
