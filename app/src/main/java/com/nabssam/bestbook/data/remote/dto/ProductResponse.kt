package com.nabssam.bestbook.data.remote.dto

data class ProductResponseFreeApi(
    val data: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

data class Data(
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Any,
    val page: Int,
    val prevPage: Any,
    val products: List<Product>,
    val serialNumberStartFrom: Int,
    val totalPages: Int,
    val totalProducts: Int
)

data class MainImage(
    val _id: String,
    val localPath: String,
    val url: String
)


data class Product(
    val __v: Int,
    val _id: String,
    val category: String,
    val createdAt: String,
    val description: String,
    val mainImage: MainImage,
    val name: String,
    val owner: String,
    val price: Int,
    val stock: Int,
    val subImages: List<SubImage>,
    val updatedAt: String
)

data class SubImage(
    val _id: String,
    val localPath: String,
    val url: String
)