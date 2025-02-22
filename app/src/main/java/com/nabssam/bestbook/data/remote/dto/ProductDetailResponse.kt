package com.nabssam.bestbook.data.remote.dto

data class ProductDetailResponse(
    val message: String,
    val product: ProductDto,
    val status: String
)

data class ProductsResponse(
    val message: String,
    val products: List<ProductDto>,
    val status: String
)

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