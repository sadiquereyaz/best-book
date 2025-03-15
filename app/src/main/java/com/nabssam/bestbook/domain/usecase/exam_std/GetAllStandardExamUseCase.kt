package com.nabssam.bestbook.domain.usecase.exam_std

import com.nabssam.bestbook.domain.repository.ExamRepository
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.utils.Standard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllStandardExamUseCase @Inject constructor(
    private val examRepository: ExamRepository,
) {
    operator fun invoke(): Flow<Resource<List<Standard>>> = flow {
        emit(Resource.Loading())
        val response = examRepository.fetchAllStandard()
        if (response.isSuccess) {
            emit(Resource.Success(data = response.getOrNull() ?: emptyList()))
        } else {
            emit(Resource.Error(message = response.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}