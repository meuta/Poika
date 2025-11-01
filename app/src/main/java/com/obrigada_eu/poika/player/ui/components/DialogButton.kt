package com.obrigada_eu.poika.player.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.ui.theme.dialogButtonText

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

class DialogButtonTextProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("OK", "Cancel")
}

@Preview(showBackground = true)
@Composable
fun DialogButtonPreview(
    @PreviewParameter(DialogButtonTextProvider::class) text: String
) {
    DialogButton(
        text = text,
        onClick = {}
    )
}