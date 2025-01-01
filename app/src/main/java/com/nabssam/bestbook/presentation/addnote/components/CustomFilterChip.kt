package com.nabssam.bestbook.presentation.addnote.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CustomFilterChip(
    modifier: Modifier = Modifier,
    label: String = "Low",
){
    FilterChip(
        selected = true,
        onClick = { /*TODO*/ },
        label = { Text(label) },
        modifier = modifier
    )
}