package com.obrigada_eu.poika.shared

expect object Logger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
}