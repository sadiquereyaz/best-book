package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.domain.model.AuthResponse
import com.nabssam.bestbook.domain.model.RegisterRequest
import com.nabssam.bestbook.domain.model.SignInRequest
import com.nabssam.bestbook.domain.model.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v1/users/login")
    suspend fun signIn(@Body request: SignInRequest): Response<AuthResponse>
    
    @POST("api/v1/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

   // https://api.freeapi.app/api/v1/users/verify-email/ffff8c2d25725516cf34c8cfa9c5f4d8f1f1f407'
}
