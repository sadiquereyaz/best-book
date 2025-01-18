package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.CartItemMain
import com.nabssam.bestbook.domain.model.CartItem

class CartMapper {
    fun dtoToDomain(dto: CartItemMain): CartItem {
        val product = dto.productMain
        return CartItem(
            id = dto._id,
            name = product.title,
            price = product.price,
            coverImage = product.coverImage,
            stock = product.stock,
            disPer = product.hardcopyDiscount,
            quantity = dto.quantity,
            productId = product._id

        )
    }
}