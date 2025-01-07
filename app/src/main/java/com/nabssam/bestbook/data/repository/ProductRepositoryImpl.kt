package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.ProductApi
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.ProductRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductApi,
    private val mapper: BookMapper
) : ProductRepository {

    override fun getProductById(productId: String): Flow<Resource<Book>> = flow {
        emit(Resource.Loading())
        try {
            val product = api.getProductById(productId = productId).body()?.let { apiProduct ->
                mapper.dtoToDomain(apiProduct)
            }
            Log.d("ProductRepositoryImpl-getProductById(${productId})", "Fetched Product: ${product?.name}")
            emit(Resource.Success(product))
        } catch (e: Exception) {
            Log.d(
                "ProductRepositoryImpl-getProducts()-exception",
                e.localizedMessage ?: "An unexpected error occurred"
            )
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "An unexpected error occurred"))
    }


    /*override suspend fun getProductById(productId: String): Product {
        val productEntity = dao.getProductById(productId)
        return if (productEntity != null) {
            mapper.entityToDomain(productEntity)
        } else {
            val productDto = api.getProductDetails(productId)
            val product = mapper.dtoToDomain(productDto)
            dao.insertProduct(mapper.domainToEntity(product))
            product
        }
    }*/

    override fun getProducts(): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading())
        try {
            val products = api.getProductList().body()?.map {it-> mapper.dtoToDomain(it) }
            Log.d("ProductRepositoryImpl-getProducts()", "Fetched Products: ${products?.size}")
            emit(Resource.Success(data = products))
        } catch (e: Exception) {
            Log.d(
                "ProductRepositoryImpl-getProducts()-exception",
                e.localizedMessage ?: "An unexpected error occurred"
            )
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "An unexpected error occurred"))
    }


    override suspend fun searchProducts(query: String): Flow<Resource<List<Book>>> {
        TODO("Not yet implemented")
    }


    override suspend fun getProductsByCategory(category: String): List<Book>? {
        TODO("Not yet implemented")
    }

    //load all the products from server and store into room
    override suspend fun refreshProducts() {
        try {
            val remoteProducts = api.getProductList()
            //  val entities = remoteProducts.map { mapper.dtoToDomain(it) }
            //dao.insertAll(entities)
        } catch (e: Exception) {
            throw e
        }
    }
    /*
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