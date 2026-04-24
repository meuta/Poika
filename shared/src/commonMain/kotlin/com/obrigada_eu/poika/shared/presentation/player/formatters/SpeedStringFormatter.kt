package com.obrigada_eu.poika.shared.presentation.player.formatters

object SpeedStringFormatter {

    private const val PATTERN: String = "x%.1f"

    fun formatSpeedToString(speed: Float): String {
        return PATTERN.format(speed)
    }
}