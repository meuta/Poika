package com.obrigada_eu.poika.ui.utils

import android.content.Context
import android.text.Spannable
import android.text.style.RelativeSizeSpan
import android.widget.Toast

object Toaster {

    fun show(context: Context, message: Spannable, shortDuration: Boolean = true) {
        val text = message.apply {
            setSpan(
                RelativeSizeSpan(1.1f),
                0,
                message.length,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }

        Toast.makeText(
            context,
            text,
            if (shortDuration) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        ).show()
    }
}