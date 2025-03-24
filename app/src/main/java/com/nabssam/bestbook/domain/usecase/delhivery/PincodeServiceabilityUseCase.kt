package com.nabssam.bestbook.domain.usecase.delhivery

import android.util.Log
import com.nabssam.bestbook.domain.repository.DelhiveryRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "PINCODE_SERVICEABILITY_UC"

class PincodeServiceabilityUseCase @Inject constructor(
    private val repository: DelhiveryRepository
){
     operator fun invoke(pincode: String): Flow<Resource<String>> = flow {
        //Log.d(TAG, "invoke: $pincode")
        emit(Resource.Loading())
        try {
            val result = repository.checkPincodeServiceability(pincode)
            if (result.isSuccess) {
                emit(Resource.Success(result.getOrNull()))
            } else {
                Log.e(TAG, "invoke error: ${result.exceptionOrNull()?.message}")
                emit(Resource.Error(result.exceptionOrNull()?.message ?: "An error occurred"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "invoke error: ${e.message}")
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}