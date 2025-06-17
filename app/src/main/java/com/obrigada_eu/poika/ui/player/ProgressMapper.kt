package com.obrigada_eu.poika.ui.player

class ProgressMapper(private val stringFormatter: StringFormatter) {

    operator fun invoke(progressState: ProgressState): ProgressStateUi {
        val currentPositionSec = progressState.currentPosition / 1000
        val currentPositionString = stringFormatter.formatMillisToString(progressState.currentPosition)
        val durationSec = progressState.duration / 1000
        val durationString = stringFormatter.formatMillisToString(progressState.duration)

        return ProgressStateUi(
            currentPositionSec,
            currentPositionString,
            progressState.isFinished,
            durationSec,
            durationString
        )
    }
}