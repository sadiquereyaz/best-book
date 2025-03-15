package com.nabssam.bestbook.domain.usecase

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllBannerUseCase @Inject constructor(
    private val repository: BannerRepository
) {
    operator fun invoke(): Flow<Resource<List<Banner>>> = flow {
        emit(Resource.Loading())
        val response = repository.getAll()
        if (response.isSuccess) {
            emit(Resource.Success(response.getOrNull() ?: emptyList()))
        } else {
            emit(Resource.Error(response.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}