package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: BookApi,
    private val mapper: BookMapper
) : BookRepository {

    override suspend fun getBooks(exam: String): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getBooks(exam)
            Log.d("BOOK_REPO_IMPL", "Book RESPONSE FROM API: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.data.map { bookDto ->
                        mapper.dtoToDomain(
                            bookDto
                        )
                    }))
                } ?: emit(Resource.Error(message = "Empty response"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getAllTargetExam(): Flow<Resource<List<String>>> = flow {
        Log.d("BOOK_REPO", "RESPONSE FROM API IN getAllExam: called")
        emit(Resource.Loading())
        try {
            val response = api.getAllTarget()
            Log.d("BOOK_REPO", "RESPONSE FROM API IN getAllExam: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.data))
                } ?: emit(Resource.Error(message = "Empty response"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getBookById(id: String): Flow<Resource<Book>> = flow {
        emit(Resource.Loading())
        Log.d("BOOK_REPO", "getProductById called with book ID: $id")
        try {
            val response = api.getBookById(id)
            Log.d("BOOK_REPO", "getBookById: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = mapper.dtoToDomain(it.data)))
                } ?: emit(Resource.Error("No book found"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }




    override suspend fun searchProducts(query: String): Flow<Resource<List<Book>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByCategory(category: String): Flow<Resource<List<Book>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.getBooksByCategory(/*category = category*/)
                Log.d("BOOK_REPO", "RESPONSE FROM API IN getBooksByCategory: $response")

                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null) {
//                        val books =
//                            productResponse.data.productFreeApis.map { mapper.productDtoToDomain(it) }
//                        emit(Resource.Success(data = books))
                    } else {
                        emit(Resource.Error("No book found in this category"))
                    }
                } else {
                    emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }

    //load all the products from server and store into room
    override suspend fun refreshProducts() {
        try {
            val remoteProducts = api.getBookList()
            //  val entities = remoteProducts.map { mapper.dtoToDomain(it) }
            //dao.insertAll(entities)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addBookToUserCart(book: Book, userId: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())
            val status: Int = api.addBook(book).code()
            try {
                if (status == 201)
                    emit(Resource.Success("Book added successfully"))
                else
                    emit(Resource.Error("Unable to add book"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
            }
        }

    override suspend fun getAll(): Flow<Resource<List<Book>>> = flow {
        val status = api.getAll().code()
        try {
            val bookList = api.getAll().body()
            if (status == 200)
                emit(Resource.Success(bookList))
            else
                emit(Resource.Error("Unable to fetch books"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun getById(id: Int): Flow<Resource<Book>> = flow {
        val status = api.getById(id).code()
        try {
            val book = api.getById(id).body()
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