package com.nabssam.bestbook.presentation.ui.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun MyLottieAnimation(
    modifier: Modifier = Modifier,
    lottie: LottieCompositionSpec.RawRes
) {
    var isLottiePlaying = remember {
        mutableStateOf(true)
    }
    var animationSpeed = remember {
        mutableStateOf(0.5f)
    }
    val composition = rememberLottieComposition(
        lottie
    )
    val lottieAnimation = animateLottieCompositionAsState(
        // pass the composition created above
        composition.value,
        // Iterates Forever
        iterations = LottieConstants.IterateForever,
        // Lottie and pause/play
        isPlaying = isLottiePlaying.value,
        // Increasing the speed of change Lottie
        speed = animationSpeed.value,
        restartOnPlay = true

    )


    LottieAnimation(
        composition = composition.value,
        progress = lottieAnimation.progress,
        modifier
    )

}



