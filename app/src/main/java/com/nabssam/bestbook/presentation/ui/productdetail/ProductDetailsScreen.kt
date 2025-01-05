package com.nabssam.bestbook.presentation.ui.productdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.nabssam.bestbook.utils.Resource

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    productId: String
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = productId) {
        viewModel.fetchProductDetails(productId)
    }

    when (state.value) {
        is Resource.Loading -> {
            // Show loading indicator
        }
        is Resource.Success -> {
            // Display product details (productDetails.data)
        }
        is Resource.Error -> {
            // Show error message (productDetails.message)
        }
    }
}