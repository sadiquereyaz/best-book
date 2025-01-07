package com.nabssam.bestbook.presentation.ui.cart

import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.model.FollowableTopic
import com.nabssam.bestbook.domain.model.Topic
import com.nabssam.bestbook.domain.model.UserNewsResource

sealed interface CartUiState {
    data object Loading : CartUiState

    data class Error(val message:String = "An error occurred") : CartUiState

    data class Idle(
        val cartItems: List<CartItem> = emptyList(),
        val books: List<BookDto> = emptyList(),
    ) : CartUiState {
        fun isEmpty(): Boolean = cartItems.isEmpty() && books.isEmpty()
    }

}


/*
sealed class CartUiState {
    data object Loading : CartUiState()
    data class Idle(val cartItems: List<CartItem>) : CartUiState()
    data class Error(val message: String) : CartUiState()
}*/
