package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.CartItemDto
import com.nabssam.bestbook.domain.model.UserOld
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiService {

    // Fetch detailed cart items by product IDs
    @GET("products/cart")
    suspend fun fetchAllByIds(@Body productIds: List<String>): Response<List<CartItemDto>>

    @GET("api/v1/ecommerce/cart")
    suspend fun fetchAll(@Header("Authorization") token: String="67839da16e927a9ab6a3f107"): Response<List<CartItemDto>>

    // Add a product ID to the user's cart
    @PATCH("users/{userId}/cart/add")
    suspend fun addProductToCart(
        @Path("userId") userId: String,
        @Body productId: String
    ): Response<UserOld>

    // Remove a product ID from the user's cart
    @PATCH("users/{userId}/cart/remove")
    suspend fun removeProductFromCart(
        @Path("userId") userId: String,
        @Body productId: String
    ): Response<UserOld>

    // Clear all items from the user's cart
    @PATCH("users/{userId}/cart/clear")
    suspend fun clearCart(@Path("userId") userId: String): Response<UserOld>
}
