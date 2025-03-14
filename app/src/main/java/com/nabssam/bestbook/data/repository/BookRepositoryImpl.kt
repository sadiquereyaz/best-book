package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: BookApi,
    private val mapper: BookMapper
) : BookRepository {

    override suspend fun getByExam(exam: String): Flow<Resource<List<Book>>> = flow {

        emit(Resource.Loading())
        try {
            val response = api.getByExam(exam)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.books.map { bookDto ->
                        mapper.dtoToDomainFinal(
                            bookDto
                        )
                    }))
                } ?: emit(Resource.Error(message = "Empty response"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getBookById(id: String): Flow<Resource<Book>> = flow {
        emit(Resource.Loading())
        //Log.d.d("BOOK_REPO", "getProductById called with book ID: $id")
        try {
            val response = api.getBookById(id)
            //Log.d.d("BOOK_REPO", "getBookById: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = mapper.dtoToDomainFinal(it.book)))
                } ?: emit(Resource.Error("No book found"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

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

}