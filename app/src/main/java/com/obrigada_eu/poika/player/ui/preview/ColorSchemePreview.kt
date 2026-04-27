package com.obrigada_eu.poika.player.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme


@Composable
fun ColorSchemePreview() {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ColorItem("primary", colors.primary)
        ColorItem("onPrimary", colors.onPrimary)
        ColorItem("primaryContainer", colors.primaryContainer)
        ColorItem("onPrimaryContainer", colors.onPrimaryContainer)

        ColorItem("secondary", colors.secondary)
        ColorItem("onSecondary", colors.onSecondary)
        ColorItem("secondaryContainer", colors.secondaryContainer)
        ColorItem("onSecondaryContainer", colors.onSecondaryContainer)

        ColorItem("tertiary", colors.tertiary)
        ColorItem("onTertiary", colors.onTertiary)
        ColorItem("tertiaryContainer", colors.tertiaryContainer)
        ColorItem("onTertiaryContainer", colors.onTertiaryContainer)

        ColorItem("background", colors.background)
        ColorItem("onBackground", colors.onBackground)

        ColorItem("surface", colors.surface)
        ColorItem("onSurface", colors.onSurface)
        ColorItem("surfaceVariant", colors.surfaceVariant)
        ColorItem("onSurfaceVariant", colors.onSurfaceVariant)

        ColorItem("error", colors.error)
        ColorItem("onError", colors.onError)
        ColorItem("errorContainer", colors.errorContainer)
        ColorItem("onErrorContainer", colors.onErrorContainer)

        ColorItem("outline", colors.outline)
        ColorItem("outlineVariant", colors.outlineVariant)

        ColorItem("inverseSurface", colors.inverseSurface)
        ColorItem("inverseOnSurface", colors.inverseOnSurface)
        ColorItem("inversePrimary", colors.inversePrimary)
    }
}


@Composable
private fun ColorItem(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(16.dp)
    ) {
        Text(
            text = name,
            color = contentColorFor(color)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorSchemePreview_Preview() {
    PoikaTheme {
        ColorSchemePreview()
    }
}
