package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.delhivery.PinServiceabilityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DelhiveryApiService {

    @GET("pin-codes/json/{filter_codes}")
    suspend fun checkPinCodeServiceability(@Path("filter_codes") filterCodes: String): Response<PinServiceabilityResponse>

}