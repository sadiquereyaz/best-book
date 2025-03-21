package com.nabssam.bestbook.domain.usecase.review

import com.nabssam.bestbook.domain.repository.ReviewRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
){
    operator fun invoke(reviewId: String) : Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val result = reviewRepository.deleteReviews(reviewId)
        if (result.isSuccess){
            emit(Resource.Success(result.getOrNull() ?: "Unknown error"))
        }else{
            emit(Resource.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}