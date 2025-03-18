package com.nabssam.bestbook.domain.usecase.book

import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookByIdUC @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(productId: String): Flow<Resource<Book>> = repository.getBookById(productId)
}