package com.nabssam.bestbook.presentation.ui.cart.claude

sealed class CartState {
    data object Loading : CartState()
    data class Success(val cartItemClaudes: List<CartItemClaude>) : CartState()
    data class Error(val message: String) : CartState()
}

sealed class CartOperation {
    data object Loading : CartOperation()
    data class Success(val message: String) : CartOperation()
    data class Error(val message: String) : CartOperation()
}