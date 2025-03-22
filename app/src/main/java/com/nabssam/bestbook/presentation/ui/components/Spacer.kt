package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalSpacer(
    width: Int,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.fillMaxHeight().width(width.dp))
}

@Composable
fun VerticalSpacer(
    height: Int,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .height(height = height.dp))
}