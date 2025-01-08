package com.nabssam.bestbook.data.remote.dto

data class ProductDto(
    val brand: String,
    val category: String,
    val color: String,
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val model: String,
    val popular: Boolean,
    val price: Int,
    val title: String
)

data class ProductResponse(
    val status: String,
    val message: String,
    val products: List<ProductDto>
)
