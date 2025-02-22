package com.nabssam.bestbook.presentation.ui.cart.claude.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.cart.claude.CartItemClaude

@Composable
fun CartItemsList(
    cartItemClaude: List<CartItemClaude>,
    onUpdateQuantity: (Int, Int) -> Unit,
    onRemoveItem: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cartItemClaude) { item ->
            CartItemCard(
                cartItemClaude = item,
                onQuantityChange = { quantity ->
                    onUpdateQuantity(item.id, quantity)
                },
                onRemove = { onRemoveItem(item.id) },
                onClick = { onItemClick(item.productId) }
            )
        }

        item {
            CartSummary(cartItemClaude = cartItemClaude)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartItemsListPreview() {

    CartItemsList(
        cartItemClaude = CartItemObj.cartItemClaudes,
        onUpdateQuantity = { _, _ -> },
        onRemoveItem = {},
        onItemClick = {},
    )
}