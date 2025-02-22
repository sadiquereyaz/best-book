package com.nabssam.bestbook.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class BookEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val rating: Float,
    val rateCount: Int
)