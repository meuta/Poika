package com.obrigada_eu.poika.shared.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import com.obrigada_eu.poika.shared.presentation.player.model.UiTextPart
import com.obrigada_eu.poika.shared.presentation.player.model.toAnnotatedString

@Composable
fun CustomSnackbar(
    snackbarMessage: List<UiTextPart>?,
) {
    Snackbar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surface,
    ) {
        snackbarMessage?.let {
            Text(text = it.toAnnotatedString())
        }
    }
}