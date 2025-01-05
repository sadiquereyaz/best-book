package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.domain.model.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApi {
    @POST("/add-book")
    suspend fun addBook(@Body book: Book): Response<Book>

    @GET("/books")
    suspend fun getAllBooks(): Response<List<Book>>

    @GET("/books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Response<Book>
}