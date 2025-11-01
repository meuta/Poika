package com.obrigada_eu.poika.common.formatters

object StringFormatter {

    private const val PATTERN: String = "%02d:%02d"

    fun formatMillisToString(input: Long): String = PATTERN.format(
        input / 60_000,
        input % 60_000 / 1000
    )

    fun formatSecToString(input: Int): String = PATTERN.format(
        input / 60,
        input % 60
    )
}