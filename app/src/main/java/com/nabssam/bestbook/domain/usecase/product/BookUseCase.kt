package com.nabssam.bestbook.domain.usecase.product

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetProductDetailsUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(productId: String): Flow<Resource<Book>> = repository.getProductById(productId)
}
/*class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(productId: String): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val product = repository.getProductById(productId)
            emit(Resource.Success(product)) // Emit success state with product details
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            ) // Emit error state
        }
    }
}*/

class GetProductsUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<Resource<List<Book>>> = repository.getProducts()
    /*operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val products =
                repository.getProducts().first() // collects the first emission from the flow
            emit(products) // Emit success state with product details
        } catch (e: Exception) {
            Log.d("GetProductsUseCase-exception", e.localizedMessage?: "An unexpected error occurred")
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            ) // Emit error state
        }
    }*/
}

class SearchProductsUseCase @Inject constructor(
    private val repository: BookRepository,
    private val mapper: BookMapper
) {
    operator fun invoke(query: String): Flow<Resource<List<Book>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val products = if (query.isBlank()) {
                repository.getProducts()
            } else {
                repository.searchProducts(query)
            }.first()
            emit(products) // Emit success state with product details
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            ) // Emit error state
        }
    }
}

class RefreshProductsUseCase @Inject constructor(
    private val repository: BookRepository
) {
    fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            repository.refreshProducts()
            emit(Resource.Success(Unit)) // Emit success state
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            ) // Emit error state
        }

    }
}

class GetBooksByCategoryUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(category: String): Flow<Resource<List<Book>>> {
        return repository.getProductsByCategory(category)
    }
}
