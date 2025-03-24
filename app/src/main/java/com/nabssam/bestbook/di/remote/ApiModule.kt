package com.nabssam.bestbook.di.remote

import com.nabssam.bestbook.data.remote.api.AddressApiService
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.data.remote.api.HomeApiService
import com.nabssam.bestbook.data.remote.api.OrderApiService
import com.nabssam.bestbook.data.remote.api.PinApiService
import com.nabssam.bestbook.data.remote.api.ReviewApiService
import com.nabssam.bestbook.di.Auth
import com.nabssam.bestbook.di.Pin
import com.nabssam.bestbook.di.UnAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideAuthApi(@Auth retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookApi(@UnAuth retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }
    @Provides
    @Singleton
    fun provideReviewApiService(@UnAuth retrofit: Retrofit): ReviewApiService {
        return retrofit.create(ReviewApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressApi(@Auth retrofit: Retrofit): AddressApiService {
        return retrofit.create(AddressApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideCartApi(@Auth retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideExamApi(@UnAuth retrofit: Retrofit): ExamApi {
        return retrofit.create(ExamApi::class.java)
    }

    @Provides
    @Singleton
    fun providePinApi(@Pin retrofit: Retrofit): PinApiService {
        return retrofit.create(PinApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideOrderApi(@Auth retrofit: Retrofit): OrderApiService {
        return retrofit.create(OrderApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBannerApiService(@UnAuth retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }
}
