package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.Review
import com.nabssam.bestbook.data.remote.dto.review.BookReviewResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewApiService {

    @GET("api/reviews/approvedreviews/{bookId}")
    suspend fun getBookReviews(@Path("bookId") bookId: String): Response<BookReviewResponse>



    @GET("/reviews/{bookId}")
    suspend fun getReview(@Path("bookId") bookId: String): Response<ApiResponse<ReviewSummary>>

    @POST("/reviews")
    suspend fun addReview(@Body reviewRequest: ReviewRequest): Response<ApiResponse<Review>>

    @PATCH("/reviews/approve/{reviewId}")
    suspend fun approveReview(@Path("reviewId") reviewId: String): Response<ApiResponse<Review>>

    @GET("/reviews/approved")
    suspend fun getApprovedReviews(): Response<ApiResponse<List<Review>>>

    @GET("/reviews/approved/{bookId}")
    suspend fun getApprovedReviewsForBook(@Path("bookId") bookId: String): Response<ApiResponse<List<Review>>>

    @GET("/reviews/unapproved")
    suspend fun getUnapprovedReviews(): Response<ApiResponse<List<Review>>>

    @PATCH("/reviews/{reviewId}")
    suspend fun updateReview(@Path("reviewId") reviewId: String, @Body updateData: ReviewUpdateRequest): Response<ApiResponse<Review>>

    @DELETE("/reviews/{reviewId}")
    suspend fun deleteReview(@Path("reviewId") reviewId: String): Response<ApiResponse<String>>

    @GET("/reviews/popular")
    suspend fun getPopularReviews(): Response<ApiResponse<List<Review>>>

}

data class ReviewRequest(
    val username: String,
    val description: String,
    val rating: Int,
    val itemType: String,
    val itemId: String
)

data class ReviewUpdateRequest(
    val description: String?,
    val rating: Int?
)

data class Review(
    val _id: String,
    val username: String,
    val description: String,
    val rating: Int,
    val itemId: String,
    val itemType: String,
    val createdAt: String,
    val approved: Boolean
)

data class ReviewSummary(
    val reviewsCount: Int,
    val reviews: List<Review>
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
