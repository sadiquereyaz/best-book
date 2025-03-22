package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "BOOK_REPO_IMPL"

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi,
    private val mapper: BookMapper
) : BookRepository {

    override suspend fun getByExam(exam: String): Flow<Resource<List<Book>>> = flow {

        emit(Resource.Loading())
        try {
            val response = api.getByExam(exam)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.data.map { bookDto ->
                        mapper.dtoToDomain(
                            bookDto
                        )
                    }))
                } ?: emit(Resource.Error(message = "Empty response"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            Log.e(TAG, "getByExam: ", e)
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getBookById(id: String): Flow<Resource<Book>> = flow {
        emit(Resource.Loading())
//        Log.d("BOOK_REPO", "getProductById called with book ID: $id")
        try {
            val response = api.getBookById(id)
//            Log.d(TAG, "getBookById: ${response.body()}")
//            Log.d(TAG, "rating: ${response.body()?.bookDetailRateStats}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(
                        Resource.Success(
                            data = mapper.dtoToDomain(it.book).copy(averageRate = it.bookDetailRateStats?.averageRating, reviewCount = it.bookDetailRateStats?.reviewCount)
                        )
                    )
                } ?: emit(Resource.Error("No book found"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    //TODO: should be present in exam repository
    override suspend fun getAllTargetExam(): Flow<Resource<List<String>>> = flow {
        //Log.d.d("BOOK_REPO", "RESPONSE FROM API IN getAllExam: called")
        emit(Resource.Loading())
        try {
            val response = api.getAllTarget()
            //Log.d.d("BOOK_REPO", "RESPONSE FROM API IN getAllExam: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.data))
                } ?: emit(Resource.Error(message = "Empty response"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getAllBook(): Result<List<Book>> {
        return try {
            val response = api.getAll()
//            Log.d(TAG, "getAll: response: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let { it ->
                    Result.success(it.data.map { bookDto ->
//                        Log.d(TAG, "rate: : ${bookDto.reviewStats}\n\n\n")
                        mapper.dtoToDomain(
                            bookDto
                        )
                    })
                } ?: Result.failure(Exception("Empty response"))
            } else {
//                Log.e(TAG, "getAll: Error: ${response.code()} - ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: An unexpected error occurred: ${e.message ?: ""}")
            Result.failure(Exception(e.message ?: "An unexpected error occurred"))
        }
    }
}