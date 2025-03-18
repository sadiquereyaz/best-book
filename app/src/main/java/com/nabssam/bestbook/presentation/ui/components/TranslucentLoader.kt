package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun TranslucentLoader(modifier: Modifier, message: String = "Loading...") {
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
           Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center
           ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer
                )
                Text(message, color = MaterialTheme.colorScheme.primary)
            }
        }
        Box(
            modifier = modifier
                .alpha(0.1f)
                .background(MaterialTheme.colorScheme.scrim)
                .clickable(enabled = false) {}
                .fillMaxSize()
        )
    }
}