package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.api.ReviewRequest
import com.nabssam.bestbook.domain.model.Review


interface ReviewRepository {
    suspend fun getBookReviews(bookId: String): Result<List<Review>>
    suspend fun addReview(review: ReviewRequest): Result<Review>
    suspend fun deleteReviews(reviewId: String): Result<Unit>
}