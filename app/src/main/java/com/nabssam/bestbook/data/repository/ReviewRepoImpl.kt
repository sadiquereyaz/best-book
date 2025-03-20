package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.ReviewMapper
import com.nabssam.bestbook.data.remote.api.ReviewApiService
import com.nabssam.bestbook.data.remote.api.ReviewRequest
import com.nabssam.bestbook.data.remote.dto.review.ReviewDto
import com.nabssam.bestbook.domain.model.Review
import com.nabssam.bestbook.domain.repository.ReviewRepository
import javax.inject.Inject

private const val TAG = "REVIEW_REPO"

class ReviewRepoImpl @Inject constructor(
    private val api: ReviewApiService,
    private val mapper: ReviewMapper,
    private val getUse
) : ReviewRepository {
    override suspend fun getBookReviews(
        bookId: String,
        isTopReview: Boolean
    ): Result<List<Review>> {
        val response = api.getBookReviews(bookId)
        Log.d(TAG, response.body().toString())
        return if (response.isSuccessful) {
            response.body()?.data?.let { reviews ->
                if (isTopReview)
                    Result.success(
                        reviews.sortedByDescending { it.rating }.take(10)
                            .map { mapper.dtoToDomain(it) })
                else
                    Result.success(reviews.filter { it.userId == "" }
                        .map { mapper.dtoToDomain(it) })

                /*val topReviews: MutableList<ReviewDto> =
                    reviews.sortedByDescending { it.rating }.take(10).toMutableList()
                reviews.forEach { if (it.userId == "currentUser") topReviews.add(it) }

                Result.success(
                    topReviews.map { mapper.dtoToDomain(it) }
                )*/
            } ?: Result.failure(Exception("Empty response"))
        } else {
            Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
        }
    }

    override suspend fun addReview(review: ReviewRequest): Result<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReviews(reviewId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}