package com.nabssam.bestbook.presentation.ui.cart.claude

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nabssam.bestbook.presentation.ui.cart.claude.composable.CartItemsList
import com.nabssam.bestbook.presentation.ui.cart.claude.composable.EmptyCartMessage
import com.nabssam.bestbook.presentation.ui.cart.claude.composable.ErrorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenClaude(
    viewModel: CartViewModelClaude = hiltViewModel(),
    onNavigateToProduct: (Int) -> Unit
) {
    val cartState by viewModel.cartState.collectAsState()
    val scope = rememberCoroutineScope()

    // Collect cart operations for showing snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.cartOperation.collect { operation ->
            when (operation) {
                is CartOperation.Success -> {
                    snackbarHostState.showSnackbar(operation.message)
                }

                is CartOperation.Error -> {
                    snackbarHostState.showSnackbar(
                        message = operation.message,
                        actionLabel = "Retry"
                    )
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCartItems()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                actions = {
                    IconButton(onClick = { viewModel.clearCart() }) {
                        Icon(Icons.Default.Clear, "Clear cart")
                    }
                }
            )
        }
    ) { padding ->
        when (val currentState = cartState) {
            is CartState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }

            is CartState.Success -> {
                if (currentState.cartItemClaudes.isEmpty()) {
                    EmptyCartMessage(
                        modifier = Modifier.padding(padding)
                    )
                } else {
                    CartItemsList(
                        cartItemClaude = currentState.cartItemClaudes,
                        onUpdateQuantity = { cartItemId, quantity ->
                            viewModel.updateCartItem(cartItemId, quantity)
                        },
                        onRemoveItem = { cartItemId ->
                            viewModel.removeFromCart( cartItemId)
                        },
                        onItemClick = onNavigateToProduct,
                        modifier = Modifier.padding(padding)
                    )
                }
            }

            is CartState.Error -> {
                ErrorMessage(
                    message = currentState.message,
                    onRetry = { viewModel.fetchCartItems() },
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    CartScreenClaude(
        onNavigateToProduct = {}
    )
}