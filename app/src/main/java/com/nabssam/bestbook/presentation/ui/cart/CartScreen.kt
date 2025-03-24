package com.nabssam.bestbook.presentation.ui.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.presentation.ui.cart.components.CartItem
import com.nabssam.bestbook.presentation.ui.cart.components.EmptyCartMessage
import com.nabssam.bestbook.presentation.ui.cart.components.PriceRow
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    goToBookDetail: (String) -> Unit,
    goToAddressScreen: () -> Unit,
    vm: ViewModelCart
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsState()

    when (uiState) {
        is CartUiState.Loading -> {
            TranslucentLoader(modifier = modifier, message = "Loading...")
        }

        is CartUiState.Error -> {
            val error = (uiState as CartUiState.Error).message
            ErrorScreen(
                message = error,
                modifier = modifier,
                onRetry = { vm.fetchAllCartItems() }
            )
        }

        is CartUiState.Idle -> {
            val idleState = (uiState as CartUiState.Idle)
            val cartItems = idleState.allCartItem

            Box(
                modifier = modifier.fillMaxSize()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    items(cartItems) {
                        if (it.quantity > 0)
                            CartItem(
                                goToBookDetail = goToBookDetail,
                                cartItem = it,
                                updateQuantity = { quantity ->
                                    vm.updateQuantity(
                                        productId = it.productId,
                                        quantity = quantity,
                                        type = it.productType
                                    )
                                },
                            )
                    }

                    // price receipt
                    item {
                        if (idleState.totalItems > 0) {
                            HorizontalDivider()
                            Text(
                                "Price Breakup",
                                modifier = Modifier.padding(12.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            PriceRow(
                                priceTitle = "Price (${idleState.totalItems} items)",
                                price = "₹${idleState.totalLabelAmount}"
                            )

                            PriceRow(
                                priceTitle = "Discount (${idleState.totalDiscountPer}%)",
                                price = "-₹${idleState.totalDiscountAmount}"
                            )
                            /* PriceRow(
                             priceTitle = "Delivery Charges",
                             price = "₹${idleState.totalAmount}",
                             color = MaterialTheme.colorScheme.secondary,
                             textDecoration = TextDecoration.LineThrough
                         )*/
                            PriceRow(
                                modifier = Modifier.padding(top = 8.dp),
                                priceTitle = "Total Amount",
                                price = "₹${idleState.totalAmount}",
                                color = MaterialTheme.colorScheme.primary,
                            )

                            Spacer(Modifier.height(100.dp))
                        }
                    }
                }

                // bottom price card
                if (idleState.totalItems > 0) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        elevation = CardDefaults.elevatedCardElevation(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            scope.launch {
                                                listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Total: ", style = MaterialTheme.typography.bodyLarge)
                                    Text(
                                        "₹${idleState.totalAmount}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Icon(Icons.Outlined.Info, "price info", Modifier.size(14.dp))
                                }
                            }
                            GradientButton(
                                onClick = { goToAddressScreen() },
                                modifier = Modifier.fillMaxWidth(0.7f)
                            ) {
                                Text("Proceed")
                            }
                        }
                    }
                }
                else
                    EmptyCartMessage(modifier = modifier.fillMaxSize())
            }

        }
    }
}


