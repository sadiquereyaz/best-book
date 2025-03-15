package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun gradientBrush(): Brush {
    return Brush.linearGradient(
        colors = listOf(Color(0xFF223876), Color(0xFF1e429f), Color(0xFF5C1ED9))
    )
}