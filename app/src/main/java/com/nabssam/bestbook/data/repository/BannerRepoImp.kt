package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.mapper.MiscMapper
import com.nabssam.bestbook.data.remote.api.BannerApiService
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BannerRepoImp @Inject constructor(
    private val api: BookApi,
    private val mapper: MiscMapper,
    private val bannerApi: BannerApiService
) : BannerRepository {

    override suspend fun add(book: Banner): Flow<Resource<String>> = flow {

    }

    override suspend fun getAll1(targetExam: String): Flow<Resource<List</*Banner*/String>>> = flow {
        emit(Resource.Loading())
        //delay(2000)
        emit(
            Resource.Success(
                data = listOf(
                    "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/coaching-tuition-class-facebook-share-image-design-template-45300209f14ac652a11847cf66dc7523_screen.jpg?ts=1691780430",
                    "http://res.cloudinary.com/dniu1zxdq/image/upload/v1736689141/ucrl3fo8dryluiacy96s.jpg",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_ujH25vS4BtSzA73oMryexhORLFN-2QNRvQ&s"
                )
            )
        )
    }

    override suspend fun getAll(): Result<List<Banner>> {
        return try {
            val response = bannerApi.getBanners()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    Result.success(
                        it.map { bannerDto ->
                            Banner(imageLink = bannerDto.imageUrl, redirectLink = bannerDto.link)
                        }
                    )
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e.message ?: "An unexpected error occurred"))
        }
    }
}