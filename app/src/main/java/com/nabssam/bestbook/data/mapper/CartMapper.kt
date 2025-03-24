package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.CartItemFinal
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.model.CartItem

class CartMapper {
    fun dtoToDomain(dto: CartItemFinal): CartItem {
        val product = dto.product
        return CartItem(
            productId = product._id,
            name = product.title ?: "",
            price = product.price ?: 0,
            coverImage = product.coverImage ?: "",
            stock = product.stock ?: 0,
            discount = if (dto.productType == ProductType.Book) product.hardcopyDiscount ?: 0 else  product.ebookDiscount ?: 0 ,
            quantity = dto.quantity ?: 0,
            productType = dto.productType ?: ProductType.Book
        )
    }
}

