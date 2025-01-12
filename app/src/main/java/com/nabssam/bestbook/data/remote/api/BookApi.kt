package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.CategoriesResponseFreeApi
import com.nabssam.bestbook.data.remote.dto.CategoryResponseOld
import com.nabssam.bestbook.data.remote.dto.ProductDetailResponse
import com.nabssam.bestbook.data.remote.dto.ProductResponseFreeApi
import com.nabssam.bestbook.data.remote.dto.ProductsResponse
import com.nabssam.bestbook.domain.model.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("products/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Response<ProductDetailResponse>
    //@Path annotation in Retrofit is used to define placeholders for dynamic parts of the API endpoint URL.

    @GET("products/category")
    suspend fun getBooksByCategory(@Query("type") category: String): Response<ProductResponseFreeApi>
    //@Query is used to pass query parameters (e.g., type).

    @GET("products")
    suspend fun getLimitedProduct(@Query("limit") limit: Int = 3): Response<ProductsResponse>


    @POST("/add-book")
    suspend fun addBook(@Body book: Book): Response<Book>
    //@Body: Used to specify the request body for POST, PUT, and PATCH requests.

    @GET("/books")
    suspend fun getAll(): Response<List<Book>>

    @GET("/books/{id}")
    suspend fun getById(@Path("id") id: Int): Response<Book>

    //freeApi
    @GET("api/v1/ecommerce/categories")
    suspend fun getAllCategory(
        @Query("page") page:Int = 1,
        @Query("limit") limit:Int = 5
    ):Response<CategoriesResponseFreeApi>

    @GET("api/v1/ecommerce/products")
    suspend fun getBookList(
        @Query("page") page:Int = 1,
        @Query("limit") limit:Int = 50
    ): Response<ProductResponseFreeApi>

}