package com.nabssam.bestbook.presentation.ui.cart.claude.composable


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.presentation.ui.cart.claude.CartItemClaude

@Composable
fun CartItemCard(
    cartItemClaude: CartItemClaude,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = cartItemClaude.productImage,
                contentDescription = cartItemClaude.productName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItemClaude.productName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${cartItemClaude.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (cartItemClaude.quantity > 1) {
                                onQuantityChange(cartItemClaude.quantity - 1)
                            }
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, "Decrease quantity")
                    }

                    Text(
                        text = cartItemClaude.quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    IconButton(
                        onClick = { onQuantityChange(cartItemClaude.quantity + 1) }
                    ) {
                        Icon(Icons.Default.KeyboardArrowUp, "Increase quantity")
                    }

                    IconButton(
                        onClick = onRemove
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove item",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartItemPreview() {
    CartItemCard(
        cartItemClaude = CartItemObj.cartItemClaudes[0],
        onQuantityChange = {},
        onRemove = {},
        onClick = {}
    )
}



