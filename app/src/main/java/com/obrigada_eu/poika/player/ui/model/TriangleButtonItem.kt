package com.obrigada_eu.poika.player.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.obrigada_eu.poika.player.ui.components.SpeedControllerButtonType

data class TriangleButtonItem(
    val type: SpeedControllerButtonType,
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)