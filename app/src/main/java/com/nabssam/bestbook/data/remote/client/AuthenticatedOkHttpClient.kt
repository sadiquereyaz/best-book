package com.nabssam.bestbook.data.remote.client

import android.util.Log
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class AuthenticatedOkHttpClient @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authManager: AuthManager
) {
    fun create(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->  // TODO: seperate this interceptor
                val request = chain.request()
                val accessToken = runBlocking { tokenStorage.getAccessToken() }
                val sessionToken = runBlocking { tokenStorage.getRefreshToken() }
                Log.d("AUTH_OKHTTP", "Token from storage: $accessToken")
                val authenticatedRequest = if (accessToken != null) {
                    request.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .header("RefreshToken", "Bearer $sessionToken")
//                        .header("Cookie", "access_token=$accessToken; session_token=$sessionToken")
//                        .header("Cookie","access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3OTg4NWE3MWM4YThmNWMyODY4YjYxZSIsImlzQWRtaW4iOnRydWUsImlhdCI6MTczODA4NDM5NCwiZXhwIjoxNzM4MDg3OTk0fQ.pRFxv80Ae4YZfhqbEC6yJPCLri6xNhUfH3sAT8ZbaYc; session_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3OTg4NWE3MWM4YThmNWMyODY4YjYxZSIsImlzQWRtaW4iOnRydWUsImlhdCI6MTczODA4NDM5NCwiZXhwIjoxNzM4MTcwNzk0fQ.5IRzVodyn_l0UhdQkPTEksF-r3UwyFscSPspUTLnLQc")
//                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3OTg4NWE3MWM4YThmNWMyODY4YjYxZSIsImlzQWRtaW4iOnRydWUsInNlc3Npb25JZCI6IjAwMDc1ZjVmLWExMjAtNDU0Ni05NGYwLWJiNjI0YWJjN2M3ZSIsImlhdCI6MTczODA4ODU0NywiZXhwIjoxNzM4OTUyNTQ3fQ.oSL7SX7WqHLPif6zL143lNuskJ1K6dQTMz2-wksEGD8") // Use "Bearer" for JWT tokens
                        .header("Content-Type", "application/json")
                        .header("Accept", "*/*")
                        .build()

                } else {
                    request
                }

                // Make the request
                val response = chain.proceed(authenticatedRequest)

                // Handle unauthorized response (401)
                if (response.code == 401) {
                    Log.d("AUTH_OKHTTP", "INVALID ACCESS TOKEN")
                    // TODO: extract new access token from response if response have it otherwise user has
                    response.close() // Close the previous response

                    // Attempt to refresh the token
                    val newAccessToken = null
                        //runBlocking { authManager.refreshAllToken() }
                    if (newAccessToken != null) {
                        // Retry the request with the new token
                        val newRequest = request.newBuilder()
                            .header("Authorization", "Bearer $newAccessToken")
                            .build()
                        return@addInterceptor chain.proceed(newRequest)
                    } else {
                        // Logout the user if the refresh fails
                        //runBlocking { authManager.handleDeviceConflict() }
                    }
                }

                response
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
                level = HttpLoggingInterceptor.Level.BODY
            })
//            .connectTimeout(10, TimeUnit.SECONDS)   //30
//            .readTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }
}