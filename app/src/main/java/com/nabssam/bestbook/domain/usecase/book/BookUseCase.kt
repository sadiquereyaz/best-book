package com.nabssam.bestbook.domain.usecase.book

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetBookByIdUC @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(productId: String): Flow<Resource<Book>> = repository.getBookById(productId)
}

class GetAllBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Book>>> = repository.getBooks("all")
}

class GetAllTargetUC @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<String>>> = repository.getAllTargetExam()
}




class GetBooksByCategoryUseCase @Inject constructor(    // ToDO: rename: get all book
    private val repository: BookRepository
) {
    suspend operator fun invoke(targetExam: String): Flow<Resource<List<Book>>> {
        return repository.getBooks(targetExam)
    }
}


class SearchProductsUseCase @Inject constructor(
    private val repository: BookRepository,
    private val mapper: BookMapper
) {
    operator fun invoke(query: String): Flow<Resource<List<Book>>> = flow {
        try {
            emit(Resource.Loading()) // Emit loading state
            val products = if (query.isBlank()) {
                repository.getBooks("all")
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


