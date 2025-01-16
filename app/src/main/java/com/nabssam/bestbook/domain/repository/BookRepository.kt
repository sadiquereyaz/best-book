package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    suspend fun getBooks(exam: String): Flow<Resource<List<Book>>>
    suspend fun getAllTargetExam(): Flow<Resource<List<String>>>
    suspend fun getBookById(id: String): Flow<Resource<Book>>


    suspend fun searchProducts(query: String): Flow<Resource<List<Book>>>
    suspend fun refreshProducts()
    suspend fun getProductsByCategory(category: String): Flow<Resource<List<Book>>>

    suspend fun addBookToUserCart(book: Book, userId: String): Flow<Resource<String>>

    suspend fun getAll(): Flow<Resource<List<Book>>>
    suspend fun getById(id:Int): Flow<Resource<Book>>

}

