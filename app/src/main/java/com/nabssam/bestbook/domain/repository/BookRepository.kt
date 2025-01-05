package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun addBook(book: Book): Flow<Resource<String>>
    suspend fun getAllBooks(): Flow<Resource<List<Book>>>
    suspend fun getBookById(id:Int): Flow<Resource<Book>>
}

