package com.obrigada_eu.poika.ui.utils

import android.content.Context
import android.widget.Toast

object Toaster {

    fun show(context: Context, message: String, shortDuration: Boolean = true) {
        Toast.makeText(
            context,
            message,
            if (shortDuration) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        ).show()
    }
}