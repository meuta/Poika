package com.obrigada_eu.poika.common.formatters

object TimeStringFormatter {

    private const val PATTERN: String = "%02d:%02d"

    fun formatMillisToString(millis: Long): String {
        return PATTERN.format(
            millis / 60_000,
            millis % 60_000 / 1000
        )
    }

    fun formatSecToString(seconds: Float): String {
        val totalSeconds = seconds.toInt()
        return PATTERN.format(
            totalSeconds / 60,
            totalSeconds % 60
        )
    }
}