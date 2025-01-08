package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.ProductResponse
import com.nabssam.bestbook.domain.model.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("products/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Response<BookDto>

    @GET("products/category")
    suspend fun getBooksByCategory(@Query("type") category: String): Response<ProductResponse>

    @GET("products")
    suspend fun getLimitedProduct(@Query("limit") limit: Int = 3): Response<ProductResponse>


    @GET("products")
    suspend fun getBookList(): Response<List<BookDto>>


    @POST("/add-book")
    suspend fun addBook(@Body book: Book): Response<Book>

    @GET("/books")
    suspend fun getAll(): Response<List<Book>>

    @GET("/books/{id}")
    suspend fun getById(@Path("id") id: Int): Response<Book>

}