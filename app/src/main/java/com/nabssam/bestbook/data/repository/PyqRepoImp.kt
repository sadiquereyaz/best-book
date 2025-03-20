package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.pyqDtoToDomain
import com.nabssam.bestbook.data.remote.api.HomeApiService
import com.nabssam.bestbook.domain.model.Pyq
import com.nabssam.bestbook.domain.repository.PyqRepository
import javax.inject.Inject

private const val TAG = "PYQ_REPO_IMPL"

class PyqRepoImp @Inject constructor(
    private val homeApi: HomeApiService
) : PyqRepository {
    override suspend fun getAll(): Result<List<Pyq>> {
        return try {
            val response = homeApi.getPyq()
            if (response.isSuccessful) {
                response.body()?.pyqs?.let {
                    Result.success(
                        it.map { pyq->
                            pyq.pyqDtoToDomain()
                        }
                    )
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Log.e(TAG, "getAll: Error: ${response.code()} - ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: An unexpected error occurred: ${e.message ?: ""}")
            Result.failure(Exception(e.message ?: "An unexpected error occurred"))
        }
    }
}