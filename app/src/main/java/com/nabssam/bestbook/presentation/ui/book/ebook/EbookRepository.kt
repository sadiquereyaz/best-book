package com.nabssam.bestbook.presentation.ui.book.ebook

import android.util.Log
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.OrderApiService
import com.nabssam.bestbook.domain.repository.EbookRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
private const val TAG = "EBOOK_REPO"

class EbookRepositoryImp(
    private val api: OrderApiService,
    private val mapper: BookMapper,
) : EbookRepository {
    override suspend fun fetchEbook(): Flow<Resource<List<Ebook>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getAllPurchasedEbook()
            if (response.isSuccessful) {
                response.body()?.let { ebookResponse ->
                    emit(Resource.Success(data = ebookResponse.books.map { mapper.ebookDtoToDomain(it)}))
                } ?: emit(Resource.Error("No data found"))
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchEbook: ", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
