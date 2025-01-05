package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Product
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    suspend fun getProductById(id: String): Product?
    suspend fun getProducts(): Flow<List<Product>>
    suspend fun searchProducts(query: String): Flow<List<Product>>
    suspend fun refreshProducts()
    suspend fun getProductsByCategory(category: String): List<Product>?
}