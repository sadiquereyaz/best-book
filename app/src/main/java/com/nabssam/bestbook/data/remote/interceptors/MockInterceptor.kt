package com.nabssam.bestbook.data.remote.interceptors

import android.content.Context
import android.util.Log
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
        Log.d("MOCK_INTERCEPTOR", "Request_URL: $url")

        // Dummy JSON responses for different endpoints
        var responseString = when {
            url.contains("api/book/getbookbyexam/all") -> getJsonFromAssets("api_response_all_book.json")
            url.contains("api/book/getbookbyexam/JEE%20Main") -> getJsonFromAssets("api_response_book_jee_main.json")
            url.contains("api/book/getbookbyexam/NEET") -> getJsonFromAssets("api_response_book_neet.json")
            url.contains("api/book/getbookbyexam/State%20Board%20Exams") -> getJsonFromAssets("api_response_all_book.json")
            url.contains("api/book/getbookbyexam/ISC%20Board%20Exams") -> getJsonFromAssets("api_response_all_book.json")
            url.contains("api/book/getbookbyexam/CBSE%20Board%20Exams") -> getJsonFromAssets("api_response_all_book.json")
            url.contains("api/exams/getalltarget") -> getJsonFromAssets("api_all_targets.json")
            url.contains("api/book/getbookbyid/book1") -> getJsonFromAssets("api_book_by_id.json")
//            url.contains("wrong") -> getJsonFromAssets("api_response_all_book.json")
            else ->  getJsonFromAssets("api_response_all_book_not_found.json")
//            else ->  getJsonFromAssets("dummy.json")

        }
//        responseString = getJsonFromAssets("api_response_all_book.json")

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
