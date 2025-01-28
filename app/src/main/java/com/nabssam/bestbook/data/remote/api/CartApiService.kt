package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.AddToCartRequest
import com.nabssam.bestbook.data.remote.dto.CartAllItems
import com.nabssam.bestbook.data.remote.dto.CartItemDtoFree
import com.nabssam.bestbook.data.remote.dto.CartResponse
import com.nabssam.bestbook.domain.model.UserOld
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {

    @GET("api/cart")
//    suspend fun fetchAll(): Response<CartAllItems>
    suspend fun fetchAll(): Response<com.nabssam.bestbook.data.remote.api.CartResponse>

    @POST("api/cart/add/{userId}")
    suspend fun update(@Path("userId") userId: String, @Body request: AddToCartRequest): Response<CartResponse>

    @POST("api/cart/clear")
    suspend fun clear(@Body productId: String): Response<CartResponse>

    @POST("api/cart/apply-coupon")
    suspend fun applyCoupon(@Body couponCode: String): Response<CartResponse>




    // Fetch detailed cart items by product IDs
    @GET("products/cart")
    suspend fun fetchAllByIds(@Body productIds: List<String>): Response<List<CartItemDtoFree>>

    @GET("api/v1/ecommerce/cart")
    suspend fun fetchAllFree(@Header("Authorization") token: String="67839da16e927a9ab6a3f107"): Response<List<CartItemDtoFree>>

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
data class CartResponse(
    val __v: Int,
    val _id: String,
    val coupon: Any,
    val createdAt: String,
    val items: List<Any>,
    val owner: String,
    val subtotal: Int,
    val total: Int,
    val updatedAt: String
)