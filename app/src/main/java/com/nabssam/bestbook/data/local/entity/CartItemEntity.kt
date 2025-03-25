package com.nabssam.bestbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nabssam.bestbook.data.remote.dto.ProductType

@Entity(tableName = "cart_items", primaryKeys = ["productId",  "type"]) //composite primary key
data class CartItemEntity(
    val productId: String,
    val quantity: Int,
    val type: ProductType
)