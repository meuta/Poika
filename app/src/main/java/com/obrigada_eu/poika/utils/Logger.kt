package com.obrigada_eu.poika.utils

import android.util.Log
import com.obrigada_eu.poika.BuildConfig

object Logger {
    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.e(tag, message)
    }

    fun e(message: String, tag: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG || throwable != null) {
            Log.e(tag, message, throwable)
        }
    }
}