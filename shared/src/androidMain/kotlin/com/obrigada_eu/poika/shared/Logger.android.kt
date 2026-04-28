package com.obrigada_eu.poika.shared

import android.util.Log

actual object Logger {
    actual fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}