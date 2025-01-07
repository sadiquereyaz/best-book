package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.BookDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: String): Response<BookDto>

    @GET("products")
    suspend fun getProductList(): Response<List<BookDto>>

}