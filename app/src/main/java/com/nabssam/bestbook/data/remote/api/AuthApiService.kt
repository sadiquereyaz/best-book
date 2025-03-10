package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.auth.SignInRequest
import com.nabssam.bestbook.data.remote.dto.auth.SignInResponse
import com.nabssam.bestbook.data.remote.dto.auth.UserDto
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.remote.dto.auth.SignUpResponse
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpRequest
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {
    @POST("api/auth/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("api/auth/sendopt/{phone}")
    suspend fun sendOtp(@Path("phone") phone: String): Response<VerifyOtpResponse>

    @POST("api/auth/verifyotp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse> //TODO:

    @POST("api/auth/signup")
    suspend fun register(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("api/auth/refreshtoken")
    suspend fun getNewTokens(@Body refreshToken: String = "no_token"): Response<TokenResponse>
}

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
