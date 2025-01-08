package com.nabssam.bestbook.presentation.ui.cart

import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.utils.percentOf
import com.nabssam.bestbook.utils.totalCartPrice
import com.nabssam.bestbook.utils.totalDiscountPercent
import com.nabssam.bestbook.utils.totalItem
import java.math.BigDecimal
import java.math.RoundingMode

sealed interface CartUiState {
    data object Loading : CartUiState

    data class Error(val message: String = "An error occurred") : CartUiState

    data class Idle(
        val cartItemEntities: List<CartItemEntity> = emptyList(),
        val books: List<BookDto> = emptyList(),

        ) : CartUiState {
        fun isEmpty(): Boolean = cartItemEntities.isEmpty() && books.isEmpty()
        val totalLabelAmount: Double = cartItemEntities.totalCartPrice()
        val totalItems: Int = cartItemEntities.totalItem()
        val totalDiscountPer: Double = cartItemEntities.totalDiscountPercent()
        val totalAmount: Double = (totalDiscountPer).percentOf(totalLabelAmount)
        val totalDiscountAmount: Double =
            BigDecimal(totalLabelAmount.minus(totalAmount))
            .setScale(1, RoundingMode.HALF_DOWN)
            .toDouble()
    }
}
