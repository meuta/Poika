package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.obrigada_eu.poika.shared.ui.theme.dialogButtonText


@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit,
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
