package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.BookListResponse
import com.nabssam.bestbook.data.remote.dto.BookResponse
import com.nabssam.bestbook.data.remote.dto.ProductResponseFreeApi
import com.nabssam.bestbook.data.remote.dto.TargetExamsResponse
import com.nabssam.bestbook.domain.model.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("api/book/getbookbyid/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Response<BookResponse>

    //@Path annotation in Retrofit is used to define placeholders for dynamic parts of the API endpoint URL.
    @GET("api/book/getbookbyexam/{exam}")
    suspend fun getBooks(@Path("exam") targetExam: String): Response<BookListResponse>

    @GET("api/exams/getalltarget")
    suspend fun getAllTarget(): Response<TargetExamsResponse>




    @GET("api/v1/ecommerce/products/category/{categoryId}")
    suspend fun getBooksByCategory(
        @Path("categoryId") category: String = "678409ed6e927a9ab6a3f44b",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
    ): Response<ProductResponseFreeApi>
    //@Query is used to pass query parameters (e.g., type).


    @POST("/add-book")
    suspend fun addBook(@Body book: Book): Response<Book>
    //@Body: Used to specify the request body for POST, PUT, and PATCH requests.

    @GET("/books")
    suspend fun getAll(): Response<List<Book>>

    @GET("/books/{id}")
    suspend fun getById(@Path("id") id: Int): Response<Book>

    @GET("api/book/admin/getbook")
    suspend fun getBookList(): Response<BookListResponse>
}

