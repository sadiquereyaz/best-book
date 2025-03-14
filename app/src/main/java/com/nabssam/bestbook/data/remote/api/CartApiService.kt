package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.AddToCartRequest
import com.nabssam.bestbook.data.remote.dto.CartItemDtoFree
import com.nabssam.bestbook.data.remote.dto.CartResponse
import com.nabssam.bestbook.data.remote.dto.CartResponseFinal
import com.nabssam.bestbook.data.remote.dto.RemoveRequest
import com.nabssam.bestbook.data.remote.dto.RemoveResponse
import com.nabssam.bestbook.data.remote.dto.UpdateQuantityRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {

    @GET("api/cart/getcart")
    suspend fun fetchAll(): Response<CartResponseFinal>

    @POST("api/cart/add")
    suspend fun add(@Body request: AddToCartRequest): Response<Unit>

    @POST("api/cart/update-quantity")
    suspend fun updateQuantity(@Body request: UpdateQuantityRequest): Response<CartResponse>

    // Remove a product ID from the user's cart
    @POST("api/cart/remove")
    suspend fun removeProductFromCart(@Body removeRequest: RemoveRequest): Response<RemoveResponse>

    @GET("api/address/getuseraddresses")
    suspend fun getAllAddress(@Body productId: String): Response<Unit>

    @GET("api/address/addaddress")
    suspend fun addAddress(@Body productId: String): Response<Unit>

    @GET("api/address/updateaddress/:addressId")
    suspend fun updateAddress(@Body productId: String): Response<Unit>

    @GET("api/address/deleteaddress/:addressId")
    suspend fun deleteaddress(@Body productId: String): Response<Unit>


    @POST("api/cart/clear")
    suspend fun clear(@Body productId: String): Response<CartResponse>

    @POST("api/cart/apply-coupon")
    suspend fun applyCoupon(@Body couponCode: String): Response<CartResponse>


    // Fetch detailed cart items by product IDs
    @GET("products/cart")
    suspend fun fetchAllByIds(@Body productIds: List<String>): Response<List<CartItemDtoFree>>

    @GET("api/v1/ecommerce/cart")
    suspend fun fetchAllFree(@Header("Authorization") token: String="67839da16e927a9ab6a3f107"): Response<List<CartItemDtoFree>>



    // Clear all items from the user's cart
    @PATCH("users/{userId}/cart/clear")
    suspend fun clearCart(@Path("userId") userId: String): Response<Unit>
}
