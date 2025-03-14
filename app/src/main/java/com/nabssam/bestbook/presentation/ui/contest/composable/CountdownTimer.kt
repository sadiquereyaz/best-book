package com.nabssam.bestbook.presentation.ui.contest.composable

import android.os.CountDownTimer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CountdownTimer(
    modifier: Modifier = Modifier
) {
    var timeLeft by remember { mutableStateOf(300_000L) } // 5 minutes in milliseconds
    var isTimerRunning by remember { mutableStateOf(false) }

    // Start the timer when the screen loads
    DisposableEffect(Unit) {
        val timer = object : CountDownTimer(300_000L, 1_000L) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                timeLeft = 0L
                isTimerRunning = false
            }
        }
        timer.start()
        isTimerRunning = true

        onDispose {
            timer.cancel()
        }
    }

    val minutes = (timeLeft / 1000) / 60
    val seconds = (timeLeft / 1000) % 60

    Text(
        text = String.format("%02d:%02d", minutes, seconds),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}
