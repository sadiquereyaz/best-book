package com.nabssam.bestbook.di

import android.content.Context
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.domain.repository.NetworkConnectivityRepository
import com.nabssam.bestbook.presentation.ui.cart.claude.CartApiServiceClaude
import com.nabssam.bestbook.utils.Constants.BASE_URL
import com.nabssam.bestbook.utils.Constants.LOCAL_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
         //   .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build() // Add interceptors/logging if needed
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalApi(retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApiClaude(retrofit: Retrofit): CartApiServiceClaude {
        return retrofit.create(CartApiServiceClaude::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityRepository(
        @ApplicationContext context: Context
    ): NetworkConnectivityRepository {
        return NetworkConnectivityObserver(context)
    }

    /*
    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApi {
        return retrofit.create(OrderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
    */
}