package com.nabssam.bestbook.presentation.ui.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.Loading

@Composable
fun CartCounter(
    modifier: Modifier = Modifier,
    updateQuantity: (Int) -> Unit,
    quantity: Int,
    isLoading: Boolean = false
) {
    var count by remember { mutableIntStateOf(quantity) }
    Row(
        modifier = modifier
            .width(96.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // decrease btn
        Icon(
            modifier = Modifier
                .clickable {
                    updateQuantity(--count)
                }
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .size(18.dp),
            imageVector = ImageVector.vectorResource(R.drawable.remove),
            contentDescription = "remove",
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Text("$count")
        Icon(
            modifier = Modifier
                .clickable {
                    updateQuantity(++count)
                }
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .size(18.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = "remove",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        if (isLoading)
            Loading(modifier = Modifier.size(18.dp))
        else
            Spacer(modifier = Modifier.width(18.dp))
    }
}