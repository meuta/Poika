package com.obrigada_eu.poika.shared.presentation.player.model

data class ProgressStateUi(
    val currentPositionSec: Float,
    val currentPositionString: String,
    val durationSec: Float,
    val durationString: String
)