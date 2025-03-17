package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    suspend fun getByExam(exam: String): Flow<Resource<List<Book>>>
    suspend fun getAllTargetExam(): Flow<Resource<List<String>>>
    suspend fun getBookById(id: String): Flow<Resource<Book>>
    suspend fun getAllBook(): Result<List<Book>>
}

