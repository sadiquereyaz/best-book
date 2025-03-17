package com.nabssam.bestbook.domain.usecase.book

import android.util.Log
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading())
        val response = repository.getAllBook()
//        Log.d("GET_ALL_BOOK_UC", "invoke: $response")
        if (response.isSuccess) {
            emit(Resource.Success(response.getOrNull() ?: emptyList()))
        } else {
            emit(Resource.Error(response.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}