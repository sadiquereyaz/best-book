package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    fun getProductById(id: String): Flow<Resource<Book>>
    suspend fun getProducts(): Flow<Resource<List<Book>>>
    suspend fun searchProducts(query: String): Flow<Resource<List<Book>>>
    suspend fun refreshProducts()
    suspend fun getProductsByCategory(category: String): Flow<Resource<List<Book>>>

    suspend fun addBook(book: Book): Flow<Resource<String>>
    suspend fun getAll(): Flow<Resource<List<Book>>>
    suspend fun getById(id:Int): Flow<Resource<Book>>

    suspend fun getAllCategory(): Flow<Resource<List<String>>>
}

