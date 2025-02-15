package com.nabssam.bestbook.presentation.ui.cart

import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.remote.dto.product.BookDtoFreeApi
import com.nabssam.bestbook.domain.model.CartItem
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
        val books: List<BookDtoFreeApi> = emptyList(),
        val allCartItem: List<CartItem> = emptyList()
        ) : CartUiState {
        val totalLabelAmount: Double = allCartItem.totalCartPrice()
        val totalItems: Int = allCartItem.totalItem()
        val totalDiscountPer: Double = allCartItem.totalDiscountPercent()
        val totalAmount: Double = totalLabelAmount.minus((totalDiscountPer).percentOf(totalLabelAmount))
        val totalDiscountAmount: Double =
            BigDecimal(totalLabelAmount.minus(totalAmount))
            .setScale(1, RoundingMode.HALF_DOWN)
            .toDouble()
    }
}
