package com.nabssam.bestbook.domain.usecase.review

import com.nabssam.bestbook.domain.model.Review
import com.nabssam.bestbook.domain.repository.ReviewRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBookReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    operator fun invoke(id: String, isTopReview: Boolean): Flow<Resource<List<Review>>> = flow {
        emit(Resource.Loading())
        val response = repository.getBookReviews(id, isTopReview)
        if (response.isSuccess) {
            emit(Resource.Success(response.getOrNull() ?: emptyList()))
        } else {
            emit(Resource.Error(response.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}