package com.nabssam.bestbook.domain.model

import androidx.compose.ui.graphics.Color
import com.nabssam.bestbook.data.remote.dto.ProductType

data class CartItem(
    val productId: String,
    val name: String,
    val price: Int,
    val coverImage: String,
    val stock: Int,
    val hardCopyDis: Int,
    val quantity: Int = 0,
    val ebookDis: Int = 0,
    val productType: ProductType = ProductType.Book
){
    val stockType: StockType = when(stock){
        0 -> StockType.OUT_OF_STOCK
        in 1..10 -> StockType.LOW_STOCK
        else -> StockType.AVAILABLE
    }
}

enum class StockType(val text: String, val color: Color){
    AVAILABLE("In stock", Color(0xFF1C3D1C)),
    OUT_OF_STOCK("Out of Stock", Color(0xFF565656)),
    LOW_STOCK("Only few book left", Color(0xFF7A1512))
}
