package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.AddressRequest
import com.nabssam.bestbook.data.remote.dto.AddressResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApiService {
    @GET("api/address/getuseraddresses")
    suspend fun getAddresses(): Response<AddressResponse>

    @POST("api/address/addaddress")
    suspend fun addAddress(@Body address: AddressRequest): Response<Any>

    @PUT("api/address/updateaddress/{addressId}")
    suspend fun update(@Path("addressId") addressId: String): Response<Unit>

    @DELETE("api/address/deleteaddress/{addressId}")
    suspend fun deleteAddress(@Path("addressId") id: String): Response<AddressResponse>
}

