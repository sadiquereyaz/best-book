package com.nabssam.bestbook.di.remote

import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.presentation.ui.cart.claude.CartApiServiceClaude
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
    fun provideAuthApi(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApi(retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApiClaude(retrofit: Retrofit): CartApiServiceClaude {
        return retrofit.create(CartApiServiceClaude::class.java)
    }

    @Provides
    @Singleton
    fun provideExamApi(retrofit: Retrofit): ExamApi {
        return retrofit.create(ExamApi::class.java)
    }
}
