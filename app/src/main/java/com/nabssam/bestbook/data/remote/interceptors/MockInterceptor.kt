package com.nabssam.bestbook.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()

        // Dummy JSON responses for different endpoints
        val responseJson = when {
            url.contains("/cart/items") -> """
                {
                    "status": "success",
                    "cartItems": [
                        {"id": 1, "name": "Book 1", "price": 10.99},
                        {"id": 2, "name": "Book 2", "price": 12.49}
                    ]
                }
            """
            url.contains("/products") -> """
                {
                    "status": "success",
                    "products": [
                        {"id": 101, "name": "Product A", "price": 29.99},
                        {"id": 102, "name": "Product B", "price": 49.99}
                    ]
                }
            """
            else -> """
                {
                    "status": "error",
                    "message": "Endpoint not mocked"
                }
            """
        }

        return chain.proceed(request)
            .newBuilder()
            .code(200)
            .message("OK")
            .body(responseJson.toResponseBody("application/json".toMediaType()))
            .build()
    }
}
