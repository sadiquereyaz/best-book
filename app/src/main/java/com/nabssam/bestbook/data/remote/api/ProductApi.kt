package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") productId: String): ProductDto

    @GET("products")
    suspend fun getProductList(): List<ProductDto>
}