/*
package com.nabssam.bestbook.data.repository.auth

import android.util.Log
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

// Network Interceptor to add Authorization header to all request sent
*/
/*The AuthInterceptor class is an OkHttp Interceptor designed to
automatically add an Authorization header to all outgoing HTTP requests.
This is commonly used in applications where requests to APIs require
an access token (e.g., Bearer token) for authentication.
The AuthInterceptor retrieves the token (stored in a repository or preference manager)
and appends it to the request header.*//*

class AuthInterceptor @Inject constructor(
    private val authTokenProvider: AuthTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = runBlocking { authTokenProvider.getAccessToken() }
        Log.d("AUTH_INTERCEPTOR", "accessToken: $accessToken")
        */
/*If an accessToken is available, it is added to the request header
        as Authorization: Bearer <accessToken>.
        If the token is not available, the request proceeds without modification.*//*

        val response =  if (accessToken != null) {
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(request)
        }

        // Handle response
        return handleResponse(response, chain)
        }


    private fun handleResponse(response: Response, chain: Interceptor.Chain): Response {
        when (response.code) {
            401 -> {
                // Token expired or invalid
                response.close()
                return handleUnauthorized(chain)
            }
            403 -> {
                // Handle forbidden - possibly another device logged in
                runBlocking {
                    authTokenProvider.handleDeviceConflict()
                }
                return response
            }
            else -> return response
        }
    }

    private fun handleUnauthorized(chain: Interceptor.Chain): Response {
        // Try to refresh token
        val newToken = runBlocking {
            try {
                authTokenProvider.refreshToken()
            } catch (e: Exception) {
                // If refresh fails, force logout
                authTokenProvider.logout()
                null
            }
        }

        return if (newToken != null) {
            // Retry original request with new token
            val newRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
            chain.proceed(newRequest)
        } else {
            // If refresh failed, return original 401 response
            chain.proceed(chain.request())
        }
    }
}
*/
// Auth events for UI notification
