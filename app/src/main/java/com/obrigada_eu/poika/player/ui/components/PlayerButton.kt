package com.obrigada_eu.poika.player.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun PlayerButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text)
    }
}

class PlayerButtonTextProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("Play", "Pause", "Stop")
}

@Preview(showBackground = true)
@Composable
fun PlayerButtonPreview(
    @PreviewParameter(PlayerButtonTextProvider::class) text: String,
) {
    PlayerButton(
        text = text,
        onClick = {},
        modifier = Modifier
    )
}