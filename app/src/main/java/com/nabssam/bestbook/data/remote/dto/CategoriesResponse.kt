package com.nabssam.bestbook.data.remote.dto

data class CategoriesResponse(
    val statusCode: Int,
    val data: CategoriesData,
    val message: String,
    val success: Boolean
)

data class CategoriesData(
    val categories: List<Category>,
    val totalCategories: Int,
    val limit: Int,
    val page: Int,
    val totalPages: Int,
    val serialNumberStartFrom: Int,
    val hasPrevPage: Boolean,
    val hasNextPage: Boolean,
    val prevPage: Int?,
    val nextPage: Int?
)

data class Category(
    val id: String,
    val name: String
)
