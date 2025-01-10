package com.nabssam.bestbook.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Inject

// Network Interceptor to add Authorization header
class AuthInterceptor @Inject constructor(
    private val userPreferences: UserPreferencesRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val accessToken = runBlocking { userPreferences.accessToken.first() }
        
        return if (accessToken != null) {
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(request)
        }
    }
}