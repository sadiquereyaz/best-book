package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.ReviewMapper
import com.nabssam.bestbook.data.remote.api.ReviewApiService
import com.nabssam.bestbook.data.remote.dto.review.ReviewRequest
import com.nabssam.bestbook.domain.model.Review
import com.nabssam.bestbook.domain.repository.ReviewRepository
import com.nabssam.bestbook.domain.usecase.user_detail_use_case.GetUserNameUseCase
import com.nabssam.bestbook.utils.setOwner
import javax.inject.Inject

private const val TAG = "REVIEW_REPO"

class ReviewRepoImpl @Inject constructor(
    private val api: ReviewApiService,
    private val mapper: ReviewMapper,
    private val getUserNameUseCase: GetUserNameUseCase
) : ReviewRepository {
    override suspend fun getBookReviews(
        bookId: String,
        isTopReview: Boolean
    ): Result<List<Review>> {
        try {
            //throw Exception("Testing")
            val response = api.getBookReviews(bookId)
            Log.d(TAG, response.body().toString())
            return if (response.isSuccessful) {
                response.body()?.data?.let { reviews ->
                    val ownerName: String? = getUserNameUseCase.invoke()
                    if (isTopReview)
                        ownerName?.let {
                            Result.success(
                                reviews.sortedByDescending { it.rating }.take(10)
                                    .map { mapper.dtoToDomain(it).setOwner(ownerName) })
                        } ?: Result.success(reviews.map { mapper.dtoToDomain(it) })
                    else
                        ownerName?.let { username ->
                            Log.d(TAG, "getBookReviews: id: $username")
                            Result.success(reviews.map {
                                mapper.dtoToDomain(it).setOwner(username)
                            })
                        } ?: Result.success(reviews.map { mapper.dtoToDomain(it) })

                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.body()?.message ?: response.message()}"))
            }
        } catch (e: Exception) {
            return Result.failure(Exception("An unknown error occurred while fetching reviews"))
        }
    }

    override suspend fun addReview(review: ReviewRequest): Result<String> {
        try {
            val response = api.addReview(
                review.copy(
                    username = getUserNameUseCase.invoke() ?: throw Exception("User not found")
                )
            )
            return if (response.isSuccessful)
            //response.body()?.data?.let {
                Result.success("Review added successfully, it will be published after sometime")
            //} ?: Result.failure(Exception("Empty response"))
            else
                Result.failure(Exception("Error: ${response.code()} - ${response.body()?.message ?: response.message()}"))
        } catch (e: Exception) {
            return Result.failure(Exception("An unknown error occurred"))
        }
    }

    override suspend fun deleteReviews(reviewId: String): Result<String> {
        try {
            Log.d(TAG, "deleteReviews: $reviewId")
            val response = api.deleteReview(/*"6798f1ba8b0e577d13f366f1"*/reviewId)
            return if (response.isSuccessful)
                Result.success("Review deleted successfully")
            else
                Result.failure(Exception("Error: ${response.code()} - ${response.body()?.message ?: response.message()}"))
        } catch (e: Exception) {
            return Result.failure(Exception("An unknown error occurred"))
        }
    }
}

