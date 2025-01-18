package com.nabssam.bestbook.presentation.ui.cart.claude

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/*
Response<T>: This class represents the HTTP response received from the server.
It contains information like:
Status code: (e.g., 200 OK, 404 Not Found)
Headers:
Body: The actual data received from the server, which is of type T in your case: List<CartItem>.
By using Response<List<CartItem>> as the return type of your getCartItems() function, you can:

Check the HTTP status code: To determine if the request was successful.
Handle errors: Gracefully handle cases where the server returned an error (e.g., 404, 500).
Access raw headers: If you need to process any specific headers returned by the server.
*/

interface CartApiServiceClaude {
    @GET("api/v1/ecommerce/cart")
    suspend fun getCartItems(@Header("Authorization") token: String="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NzgzOWRhMTZlOTI3YTlhYjZhM2YxMDciLCJlbWFpbCI6InNhZEBnbWFpbC5jb20iLCJ1c2VybmFtZSI6InNhZGlAZ21haWwuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MzY3NDMxODEsImV4cCI6MTczNjgyOTU4MX0.-UNeloEoUuPe0d--ISTeh65dFl2qwiyZY566s3YceVk, refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NzgzOWRhMTZlOTI3YTlhYjZhM2YxMDciLCJpYXQiOjE3MzY3NDMxODEsImV4cCI6MTczNzYwNzE4MX0.hS-N5kjvHNg9YRM7sHz6JJ58XzfskMbi2P6IHX4kM10")
    : Response<List<CartItemClaude>>
    
    @POST("api/cart/add")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<CartItemClaude>
    
    @PUT("api/cart/update/{cartItemId}")
    suspend fun updateCartItem(
        @Path("cartItemId") cartItemId: Int,
        @Body request: UpdateCartRequest
    ): Response<CartItemClaude>
    
    @DELETE("api/cart/{cartItemId}")
    suspend fun removeFromCart(@Path("cartItemId") cartItemId: Int): Response<Unit>
    
    @DELETE("api/cart/clear/{userId}")
    suspend fun clearCart(@Path("userId") userId: String): Response<Unit>
}