package com.nabssam.bestbook.data.remote.dto

import com.nabssam.bestbook.domain.model.Category


data class CategoriesResponseFreeApi(
    val statusCode: Int,
    val data: CategoriesDataFreeApi,
    val message: String,
    val success: Boolean
)

data class CategoriesDataFreeApi(
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


data class CategoryResponseOld(
    val categories: List<String>,
    val message: String,
    val status: String
)
