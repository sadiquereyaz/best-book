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
                //Log.d("AUTH_OKHTTP", "Token from storage: $accessToken")
                val authenticatedRequest = if (accessToken != null) {
                    request.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .header("RefreshToken", "Bearer $sessionToken")
                        .header("Content-Type", "application/json")
                        .header("Accept", "*/*")
                        .build()

                } else {
                    request
                }
                // Make the request
                val response = chain.proceed(authenticatedRequest)
                //Log.d("AUTH_OKHTTP", "response code: ${response.code}")

                // Handle unauthorized response (401)
                if (response.code == 401 || response.code == 403) {
                    //Log.d("AUTH_OKHTTP", "INVALID ACCESS TOKEN")
                    // TODO: extract new access token from response if response have it otherwise user has
                    response.close() // Close the previous response

                    // Attempt to refresh the token ToDo
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
                        Log.d("AUTH_OKCLIENT", "Your session has expired. Please login again.")
                        runBlocking { authManager.logout() }
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