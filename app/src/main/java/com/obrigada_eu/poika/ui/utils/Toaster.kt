package com.obrigada_eu.poika.ui.utils

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Toast
import com.obrigada_eu.poika.player.ui.model.UiTextPart

object Toaster {

    fun show(context: Context, parts: List<UiTextPart>, shortDuration: Boolean = true) {
        val ssb = SpannableStringBuilder()
        parts.forEach { part ->
            val start = ssb.length
            ssb.append(part.text)
            if (part.bold) {
                ssb.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    ssb.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        ssb.setSpan(
            RelativeSizeSpan(1.1f),
            0,
            ssb.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        Toast.makeText(
            context,
            ssb,
            if (shortDuration) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
        ).show()
    }

}