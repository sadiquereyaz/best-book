package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.AddToCartRequest
import com.nabssam.bestbook.data.remote.dto.UpdateQuantityRequest
import com.nabssam.bestbook.data.remote.dto.CartItemDtoFree
import com.nabssam.bestbook.data.remote.dto.CartResponse
import com.nabssam.bestbook.data.remote.dto.CartResponseFinal
import com.nabssam.bestbook.data.remote.dto.RemoveRequest
import com.nabssam.bestbook.data.remote.dto.RemoveResponse
import com.nabssam.bestbook.domain.model.Unit1
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApiService {
    @GET("api/address/getuseraddresses")
    suspend fun getAll(@Body productId: String): Response<Unit>

    @POST("api/address/addaddress")
    suspend fun add(@Body productId: String): Response<Unit>

    @PUT("api/address/updateaddress/{addressId}")
    suspend fun update(@Path("addressId") addressId: String): Response<Unit>

    @DELETE("api/address/deleteaddress/{addressId}")
    suspend fun delete(@Path("addressId") addressId: String): Response<Unit>
}
