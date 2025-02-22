package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.mapper.AddressMapper
import com.nabssam.bestbook.data.remote.api.AddressApiService
import com.nabssam.bestbook.data.remote.dto.Address
import com.nabssam.bestbook.domain.repository.AddressRepository
import com.nabssam.bestbook.presentation.ui.address.UiStateAddress
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressApiService: AddressApiService,
    private val addressMapper: AddressMapper
): AddressRepository {
    override suspend fun fetchAddresses(): Flow<Resource<List<Address>>> = flow {
        emit(Resource.Loading())
        try {
            val response = addressApiService.getAddresses()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.addresses))
                } ?: emit(Resource.Error(message = "No Address Found"))
            } else
                when (response.code()) {
                    401 -> emit(Resource.Error(message = "Unauthorized"))
                    403 -> emit(Resource.Error(message = "Multiple device login not allowed"))
                    404 -> emit(Resource.Error(message = "No Address Found"))
                    else -> emit(Resource.Error(message = "Unknown Error"))
                }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }

    override suspend fun deleteAddress(id: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = addressApiService.deleteAddress(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = Unit))
                }
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }

    override suspend fun addAddress(address: UiStateAddress.Success.Form): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = addressApiService.addAddress(addressMapper.domainToDto(address))
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Resource.Success(Unit))
                    }
                } else {
                    throw Exception(response.message())
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "Unknown Error"))
            }
        }
}