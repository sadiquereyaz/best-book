package com.nabssam.bestbook.presentation.ui.contest.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun TimerView(timeInSeconds: Int) {
    var isColonVisible by remember { mutableStateOf(true) }
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60

    // Blinking Colon Effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            isColonVisible = !isColonVisible
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = String.format("%02dm", minutes),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (isColonVisible) ":" else " ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = String.format("%02ds", seconds),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}