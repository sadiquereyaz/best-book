package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.mapper.ProductMapper
import com.nabssam.bestbook.data.remote.api.ProductApi
import com.nabssam.bestbook.domain.model.Product
import com.nabssam.bestbook.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductApi,
    private val mapper: ProductMapper
) : ProductRepository {

    override suspend fun getProductById(productId: String): Product {
        val productEntity = dao.getProductById(productId)
        return if (productEntity != null) {
            mapper.entityToDomain(productEntity)
        } else {
            val productDto = api.getProductDetails(productId)
            val product = mapper.dtoToDomain(productDto)
            dao.insertProduct(mapper.domainToEntity(product))
            product
        }
    }

    override suspend fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchProducts(query: String): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshProducts() {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCategory(category: String): List<Product>? {
        TODO("Not yet implemented")
    }

    /* //load all the products from server and store into room
     override suspend fun refreshProducts() {
         try {
             val remoteProducts = api.getProducts()
             val entities = remoteProducts.map { mapper.mapDtoToEntity(it) }
             dao.insertAll(entities)
         } catch (e: Exception) {
             throw e
         }
     }

     override suspend fun getProducts(): Flow<List<Product>> {
         return dao.getAllProducts().map { entities ->
             entities.map { mapper.entityToDomain(it) }
         }
     }

     override suspend fun getProductById(id: String): Product? {
         return dao.getProductById(id)?.let { mapper.entityToDomain(it) }
     }

     override suspend fun searchProducts(query: String): Flow<List<Product>> {
         return dao.searchProducts("%$query%").map { entities ->
             entities.map { mapper.entityToDomain(it) }
         }
     }*/

}