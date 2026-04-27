package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.obrigada_eu.poika.shared.ui.theme.Dimens

@Composable
fun TimeText(time: String) {
    Text(
        text = time,
        fontSize = Dimens.MediumFontSize,
        modifier = Modifier.padding(horizontal = Dimens.TimeTextPaddingHorizontal),
        color = MaterialTheme.colorScheme.onBackground,
    )
}