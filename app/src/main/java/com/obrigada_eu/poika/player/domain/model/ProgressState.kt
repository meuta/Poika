package com.obrigada_eu.poika.player.domain.model

data class ProgressState(
    val currentPosition: Long = 0,
    val isFinished: Boolean = false,
    val duration: Long = 0
)