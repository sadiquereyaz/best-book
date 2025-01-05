package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
) : BookRepository {
    override suspend fun addBook(book: Book): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val status: Int = bookApi.addBook(book).code()

        try {
            if (status == 201)
                emit(Resource.Success("Book added successfully"))
            else
                emit(Resource.Error("Unable to add book"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun getAllBooks(): Flow<Resource<List<Book>>> = flow {
        val status = bookApi.getAllBooks().code()
        try {
            val bookList = bookApi.getAllBooks().body()
            if (status == 200)
                emit(Resource.Success(bookList))
            else
                emit(Resource.Error("Unable to fetch books"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun getBookById(id: Int): Flow<Resource<Book>> = flow {
        val status = bookApi.getBookById(id).code()
        try {
            val book = bookApi.getBookById(id).body()
            if (status == 200)
                emit(Resource.Success(book))
            else    //404
                emit(Resource.Error("Book not found"))
        } catch (e: Exception) {
            //500
            emit(Resource.Error(e.message))
        }
    }
}