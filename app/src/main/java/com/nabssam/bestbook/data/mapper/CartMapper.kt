package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.CartItemFinal
import com.nabssam.bestbook.domain.model.CartItem

class CartMapper {
    fun dtoToDomain(dto: CartItemFinal): CartItem {
        val product = dto.product
        return CartItem(
            id = product._id,
            productId = product._id,
            name = product.title,
            price = product.price,
            coverImage = product.coverImage,   //todo(add stock)
            stock = /*product.stock*/ 10,
            hardCopyDis = product.hardcopyDiscount,
            quantity = dto.quantity,
            ebookDis = product.ebookDiscount,
            productType = dto.productType
        )
    }
}

