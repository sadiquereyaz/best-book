package com.nabssam.bestbook.presentation.ui.account.profile


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ProgressCard(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Yellow,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 1000,
    text: String
) {
    var animationPlayed = remember {
        mutableStateOf(false)
    }
    var totalQuizes = remember { mutableIntStateOf(100) }
    val currentPercentage = animateFloatAsState(
        targetValue = percentage, animationSpec = tween(
            durationMillis = animDuration, delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed.value = true;

    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(text = text, fontSize = 12.sp)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, top = 10.dp)
                .size(radius * 2f)
        ) {
            Canvas(
                modifier = Modifier.size(radius * 2f)
            ) {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 300 * currentPercentage.value,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )

            }
            Text(
                text = totalQuizes.value.toString() + "/" + (currentPercentage.value * number * 100).toInt()
                    .toString(), fontSize = fontSize, fontWeight = FontWeight.SemiBold
            )

        }

    }
}

