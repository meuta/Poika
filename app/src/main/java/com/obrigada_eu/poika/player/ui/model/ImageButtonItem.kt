package com.obrigada_eu.poika.player.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ImageButtonItem(
    val label: String,
    val icon: ImageVector,
    val weight: Float = 1f,
    val onClick: () -> Unit
)