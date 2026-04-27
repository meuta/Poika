package com.obrigada_eu.poika.shared.presentation.player.model

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.obrigada_eu.poika.shared.ui.theme.Dimens

data class UiTextPart(
    val text: String,
    val bold: Boolean = false
)

fun List<UiTextPart>.toAnnotatedString() =
    buildAnnotatedString {
        pushStyle(
            SpanStyle(
                fontSize = Dimens.MediumFontSize
            )
        )
        this@toAnnotatedString.forEach { part ->
            if (part.bold) {
                pushStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            append(part.text)

            if (part.bold) {
                pop()
            }
        }
    }