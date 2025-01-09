package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: BookApi,
    private val mapper: BookMapper
) : BookRepository {

    override fun getProductById(productId: String): Flow<Resource<Book>> = flow {
        emit(Resource.Loading())
        try {
            val product = api.getBookById(bookId = productId).body()?.let { apiProduct ->
                mapper.dtoToDomain(apiProduct)
            }
            Log.d(
                "ProductRepositoryImpl-getProductById(${productId})",
                "Fetched Product: ${product?.name}"
            )
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

    override suspend fun getProducts(): Flow<Resource<List<Book>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getBookList()
            if (response.isSuccessful) {
                val productResponse = response.body()
                if (productResponse != null && productResponse.products.isNotEmpty()) {
                    val books = productResponse.products.map { mapper.productDtoToDomain(it) }
                    emit(Resource.Success(data = books))
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


        override suspend fun searchProducts(query: String): Flow<Resource<List<Book>>> {
            TODO("Not yet implemented")
        }


        override suspend fun getProductsByCategory(category: String): Flow<Resource<List<Book>>> =
            flow {
                emit(Resource.Loading())
                try {
                    val response = api.getBooksByCategory(category)
                    //Log.d("Response", "Body: ${response.toString()}")

                    if (response.isSuccessful) {
                        val productResponse = response.body()
                        if (productResponse != null && productResponse.products.isNotEmpty()) {
                            val books =
                                productResponse.products.map { mapper.productDtoToDomain(it) }
                            emit(Resource.Success(data = books))
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

        override suspend fun getAllCategory(): Flow<Resource<List<String>>> = flow {
            emit(Resource.Loading())
            try {
                val response = api.getAllCategory()

                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    if (categoryResponse != null && categoryResponse.categories.isNotEmpty()) {
                        emit(Resource.Success(data = categoryResponse.categories))
                    } else {
                        emit(Resource.Error("No category found"))
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

        override suspend fun addBook(book: Book): Flow<Resource<String>> = flow {
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