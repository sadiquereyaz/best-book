package com.nabssam.bestbook.data.remote.client

import android.util.Log
import coil.request.Tags
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
private const val TAG = "DELHIVERY_OKHTTP"
class DelhiveryOkHttpClient @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    fun create(): OkHttpClient {
        Log.d(TAG, "create: ")
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                try {
                val request = chain.request()
                val delhiveryApiKey = runBlocking { tokenStorage.getDelhiveryApiKey() }
                val authRequest = request.newBuilder()
                    .header("Authorization", "Token $delhiveryApiKey")
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .build()
                chain.proceed(authRequest)
                } catch (e: Exception) {
                    Log.e(TAG, "Error during request interception: ${e.message}")
                    chain.proceed(chain.request())
                }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}