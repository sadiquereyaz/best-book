package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.mapper.MiscMapper
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.delay
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
        //delay(2000)
        emit(
            Resource.Success(
                data = listOf(
                    "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/coaching-tuition-class-facebook-share-image-design-template-45300209f14ac652a11847cf66dc7523_screen.jpg?ts=1691780430",
                    "https://i.pinimg.com/originals/12/fb/14/12fb144a72112b4271f0314face6b8af.jpg",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_ujH25vS4BtSzA73oMryexhORLFN-2QNRvQ&s"
                )
            )
        )
        /*try {
            val response = api.getLimitedProduct(4)
            if (response.isSuccessful) {
                val productResponse = response.body()
                if (productResponse != null && productResponse.products.isNotEmpty()) {
                    val banners = productResponse.products.map { mapper.bannerDtoToDomain(it).imageLink }
                    //emit(Resource.Success(data = banners))
                } else {
                    emit(Resource.Error(null))
                }
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }*/
    }
}