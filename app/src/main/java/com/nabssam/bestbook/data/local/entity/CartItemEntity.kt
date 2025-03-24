package com.nabssam.bestbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nabssam.bestbook.data.remote.dto.ProductType

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val productId: String,
    val quantity: Int,
    val type: ProductType
)