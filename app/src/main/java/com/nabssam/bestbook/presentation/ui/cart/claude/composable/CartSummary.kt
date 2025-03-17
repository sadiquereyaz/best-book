package com.nabssam.bestbook.presentation.ui.cart.claude.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.cart.claude.CartItemClaude

@Composable
fun CartSummary(cartItemClaude: List<CartItemClaude>) {
    val subtotal = cartItemClaude.sumOf { it.price * it.quantity }
    val tax = subtotal * 0.1 // 10% tax
    val total = subtotal + tax

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.elevation))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal")
                Text("$${String.format("%.2f", subtotal)}")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tax (10%)")
                Text("$${String.format("%.2f", tax)}")
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "$${String.format("%.2f", total)}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            GradientButton(
                onClick = { /* Implement checkout */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceed to Checkout")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartSummaryPreview() {
    CartSummary(
        cartItemClaude = CartItemObj.cartItemClaudes
    )
}
