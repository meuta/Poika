package com.obrigada_eu.poika.player.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.obrigada_eu.poika.ui.theme.dialogButtonText

@Composable
fun DialogButton(
    onClick: () -> Unit,
    text: String,
) {
    TextButton(
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.dialogButtonText
        )
    }
}