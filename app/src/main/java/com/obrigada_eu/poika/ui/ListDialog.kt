package com.obrigada_eu.poika.ui

import android.app.AlertDialog
import android.content.Context
import com.obrigada_eu.poika.R

class ListDialog(
    private val title: String,
    private val items: List<String>,
    private val onConfirm: (String) -> Unit = {}
) {

    fun show(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setItems(items.toTypedArray()) { _, i -> onConfirm(items[i]) }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
