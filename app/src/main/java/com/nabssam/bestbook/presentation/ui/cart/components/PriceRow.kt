package com.nabssam.bestbook.presentation.ui.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun PriceRow(
    modifier: Modifier = Modifier,
    priceTitle: String,
    price: String,
    color: Color = Color.Unspecified,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(priceTitle)
        Text(
            text = price,
            color = color,
            textDecoration = textDecoration,
        )
    }
}