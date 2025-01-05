package com.nabssam.bestbook.domain.usecase.product

import com.nabssam.bestbook.domain.model.Product
import com.nabssam.bestbook.domain.repository.ProductRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
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
}

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val products =
                repository.getProducts().first() // collects the first emission from the flow
            emit(Resource.Success(products)) // Emit success state with product details
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            ) // Emit error state
        }
    }
}

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val products = if (query.isBlank()) {
                repository.getProducts()
            } else {
                repository.searchProducts(query)
            }.first()
            emit(Resource.Success(products)) // Emit success state with product details
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
    private val repository: ProductRepository
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

class GetProductsByCategoryUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    fun invoke(category: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val products = repository.getProductsByCategory(category)
            emit(Resource.Success(products)) // Emit success state with product details
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            ) // Emit error state
        }
    }
}
