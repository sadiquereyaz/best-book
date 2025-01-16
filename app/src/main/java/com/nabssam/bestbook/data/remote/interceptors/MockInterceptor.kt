package com.nabssam.bestbook.data.remote.interceptors

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class MockInterceptor(@ApplicationContext private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Thread.sleep(1000)
        val request = chain.request()
        val url = request.url.toString()

        // Dummy JSON responses for different endpoints
        val responseString = when {
            url.contains("/cart/items") -> getJsonFromAssets("api_response.json")
            else -> """
                {
                    "status": "error",
                    "message": "Endpoint not mocked"
                }
            """
        }

        return Response.Builder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message("Mock response")
            .request(request)
            .body(responseString.toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
    }
    private fun getJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
