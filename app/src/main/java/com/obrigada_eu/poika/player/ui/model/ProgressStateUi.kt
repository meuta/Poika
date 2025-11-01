package com.obrigada_eu.poika.player.ui.model

data class ProgressStateUi(
    val currentPositionSec: Long,
    val currentPositionString: String,
    val isFinished: Boolean,
    val durationSec: Long,
    val durationString: String
)