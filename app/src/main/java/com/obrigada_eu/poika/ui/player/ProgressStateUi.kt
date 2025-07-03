package com.obrigada_eu.poika.ui.player

data class ProgressStateUi(
    val currentPositionSec: Long,
    val currentPositionString: String,
    val isFinished: Boolean,
    val durationSec: Long,
    val durationString: String
)