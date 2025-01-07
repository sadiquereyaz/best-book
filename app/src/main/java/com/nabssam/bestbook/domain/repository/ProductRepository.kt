package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    fun getProductById(id: String): Flow<Resource<Book>>
    fun getProducts(): Flow<Resource<List<Book>>>
    suspend fun searchProducts(query: String): Flow<Resource<List<Book>>>
    suspend fun refreshProducts()
    suspend fun getProductsByCategory(category: String): List<Book>?
}