package com.obrigada_eu.poika.ui

import android.content.Context
import android.widget.Toast

class Toaster(private val message: String, private val shortDuration: Boolean = true) {
    fun show(context: Context) {
        Toast.makeText(
            context,
            message,
            if (shortDuration) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        ).show()
    }
}