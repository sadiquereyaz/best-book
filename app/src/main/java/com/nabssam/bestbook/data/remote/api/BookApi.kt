package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.BookListResponse
import com.nabssam.bestbook.data.remote.dto.BookResponse
import com.nabssam.bestbook.data.remote.dto.TargetExamsResponse
import com.nabssam.bestbook.domain.model.Book
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("api/book/getbookbyid/{bookId}")
    suspend fun getBookById(@Path("bookId") bookId: String): Response<BookResponse>

    //@Path annotation in Retrofit is used to define placeholders for dynamic parts of the API endpoint URL.
    @GET("api/book/getbookbyexam/{exam}")
    suspend fun getByExam(@Path("exam") targetExam: String): Response<BookListResponse>

    @GET("api/exams/getalltarget")
    suspend fun getAllTarget(): Response<TargetExamsResponse>

    @GET("api/book/admin/getbook")
    suspend fun getAll(): Response<BookListResponse>

}
