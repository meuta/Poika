package com.obrigada_eu.poika.shared.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.obrigada_eu.poika.shared.domain.audio.ChangeSpeedDirection

data class TriangleButtonItem(
    val type: ChangeSpeedDirection,
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)