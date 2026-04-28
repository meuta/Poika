package com.obrigada_eu.poika.shared

actual object Logger {
    actual fun e(tag: String, message: String, throwable: Throwable?) {
       {
            println("ERROR [$tag] $message")
            throwable?.printStackTrace()
        }
    }
}