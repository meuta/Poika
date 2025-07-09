package com.obrigada_eu.poika.player.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class PlayerButtonTextProvider : PreviewParameterProvider<String> {
    override val values = listOf("Play", "Pause", "Stop").asSequence()
}

@Preview(showBackground = true)
@Composable
fun PlayerButton(
    @PreviewParameter(PlayerButtonTextProvider::class) text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text)
    }
}