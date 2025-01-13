package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.auth.SignInRequest
import com.nabssam.bestbook.data.remote.dto.auth.SignInResponse
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.remote.dto.auth.SignUpResponse
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpRequest
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>
    
    @POST("api/auth/signup")
    suspend fun register(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("api/auth/verifyotp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    // http://localhost:3000/api/auth/verifyotp
   // https://api.freeapi.app/api/v1/users/verify-email/ffff8c2d25725516cf34c8cfa9c5f4d8f1f1f407'
}

//TargetExam
//User
//accecc token
//getall exam api
// target exam by current class
// get all classes