package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.AddToCartRequest
import com.nabssam.bestbook.data.remote.dto.CartResponse
import com.nabssam.bestbook.data.remote.dto.CartResponseFinal
import com.nabssam.bestbook.data.remote.dto.PurchasedEbookResponse
import com.nabssam.bestbook.data.remote.dto.UpdateQuantityRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApiService {

    @GET("api/order/placeorder/getcart")
    suspend fun fetchAll(): Response<CartResponseFinal>

    @POST("api/cart/add")
    suspend fun add(@Body request: AddToCartRequest): Response<Unit>

    @POST("api/cart/update-quantity")
    suspend fun updateQuantity(@Body request: UpdateQuantityRequest): Response<CartResponse>

    // Clear all items from the user's cart
    @PATCH("users/{userId}/cart/clear")
    suspend fun clearCart(@Path("userId") userId: String): Response<Unit>

    @GET("api/book/purchasedebooks")
    suspend fun getAllPurchasedEbook(): Response<PurchasedEbookResponse>
}
