package com.nabssam.bestbook.data.remote.dto.product

import com.nabssam.bestbook.data.remote.dto.ProductFreeApi

data class ProductByIdResponse(
    val `data`: ProductFreeApi,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)