package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.dto.review.ReviewRequest
import com.nabssam.bestbook.domain.model.Review


interface ReviewRepository {
    suspend fun getBookReviews(bookId: String,  isTopReview: Boolean = true): Result<List<Review>>
    suspend fun addReview(review: ReviewRequest): Result<String>
    suspend fun deleteReviews(reviewId: String): Result<String>
}