package com.obrigada_eu.poika.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val Typography.dialogButtonText: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.LargeFontSize,
        fontFeatureSettings = "c2sc, smcp"
    )
