package com.obrigada_eu.poika.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DropdownMenuItemTextProvider : PreviewParameterProvider<String> {
    override val values = listOf("Choose song").asSequence()
}

@Preview(showBackground = true)
@Composable
fun CustomDropdownMenuItem(
    @PreviewParameter(DropdownMenuItemTextProvider::class) text: String,
    onClick: () -> Unit = {}
) {
    DropdownMenuItem(
        text = {
            Text(
                text,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(horizontal = 4.dp),
            )
        },
        onClick = onClick,
    )
}