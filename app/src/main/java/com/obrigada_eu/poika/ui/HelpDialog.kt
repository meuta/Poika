package com.obrigada_eu.poika.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import com.obrigada_eu.poika.R

object HelpDialog {
    fun show(context: Context) {
        val title = context.getString(R.string.help)

        val message = context.getString(
            R.string.help_dialog_message,
            context.getString(R.string.title_how_to_use),
            context.getString(R.string.title_song_versions),
            context.getString(R.string.title_troubleshooting)
        )

        val spannable = SpannableString(message.trimIndent())

        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        val highlightColor = typedValue.data


        fun styleSection(title: String) {
            val start = spannable.indexOf(title)
            if (start >= 0) {
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    start + title.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    ForegroundColorSpan(highlightColor),
                    start,
                    start + title.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        styleSection("HOW TO USE THE APP")
        styleSection("SONG VERSIONS")
        styleSection("TROUBLESHOOTING")

        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(spannable)
            .setPositiveButton(context.getString(R.string.ok), null)
            .show()
    }
}