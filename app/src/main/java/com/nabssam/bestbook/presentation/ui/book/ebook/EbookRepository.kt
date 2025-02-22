package com.nabssam.bestbook.presentation.ui.book.ebook

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.OrderApiService
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface EbookRepository {
    suspend fun fetchEbook(): Flow<Resource<List<Ebook>>>
}

class EbookRepositoryImp(
    private val api: OrderApiService,
    private val mapper: BookMapper,
) : EbookRepository {
    override suspend fun fetchEbook(): Flow<Resource<List<Ebook>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getAllPurchasedEbook()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it.data.map { mapper.ebookDtoToDomain(it)}))
                } ?: emit(Resource.Error("No data found"))
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
