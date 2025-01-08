package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.mapper.MiscMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BannerRepoImp @Inject constructor(
    private val api: BookApi,
    private val mapper: MiscMapper
) : BannerRepository {

    override suspend fun add(book: Banner): Flow<Resource<String>> = flow {

    }

    override suspend fun getAll(targetExam: String): Flow<Resource<List</*Banner*/String>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getLimitedProduct(4)
            if (response.isSuccessful) {
                val productResponse = response.body()
                if (productResponse != null && productResponse.products.isNotEmpty()) {
                    val banners = productResponse.products.map { mapper.bannerDtoToDomain(it).imageLink }
                    emit(Resource.Success(data = banners))
                } else {
                    emit(Resource.Error(null))
                }
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}