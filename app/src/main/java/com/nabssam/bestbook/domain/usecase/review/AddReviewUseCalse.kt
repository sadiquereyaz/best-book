package com.nabssam.bestbook.domain.usecase.review

import com.nabssam.bestbook.data.remote.dto.review.ReviewRequest
import com.nabssam.bestbook.domain.repository.ReviewRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    operator fun invoke(review: ReviewRequest): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val result = repository.addReview(review)
        if (result.isSuccess){
            emit(Resource.Success(result.getOrNull() ?: "Unknown error"))
        }else{
            emit(Resource.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}