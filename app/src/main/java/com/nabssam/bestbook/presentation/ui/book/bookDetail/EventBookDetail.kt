package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.data.remote.dto.ProductType

sealed class EventBookDetail{
    data object Initialize : EventBookDetail()
    data object FetchReviews : EventBookDetail()
    data class AddToCart(val type: ProductType, val id: String) : EventBookDetail()
    data object ButtonClick : EventBookDetail()
//    data class BookTypeSelect(val btnState: ButtonType = ButtonType.ADD_TO_CART) : EventBookDetail()
    data class ProductTypeSelect(val type: ProductType) : EventBookDetail()
    data class SubmitReview(val rate: Int, val review: String) : EventBookDetail()
    data class DeleteReview(val reviewId: String) : EventBookDetail()
}

