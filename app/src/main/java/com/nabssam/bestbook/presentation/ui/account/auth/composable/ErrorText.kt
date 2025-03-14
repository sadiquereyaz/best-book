package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorText(error: String?, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        modifier = modifier
            .fillMaxWidth(),
        visible = error != null,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Text(
            text =  error ?: "",
            color = MaterialTheme.colorScheme.error,

            textAlign = TextAlign.Center
        )
    }
}
