package com.nabssam.bestbook.di.remote

import com.nabssam.bestbook.BuildConfig
import com.nabssam.bestbook.di.Auth
import com.nabssam.bestbook.di.Mock
import com.nabssam.bestbook.di.Track
import com.nabssam.bestbook.di.UnAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
//    @UnAuth
    fun provideBaseRetrofit(@UnAuth okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Track
    fun provideTrackingRetrofit(@Auth okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl) // Replace with tracking base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Mock
    fun provideMockRetrofit(@Mock mockOkHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mock.com/") // Base URL is not relevant for mock responses
            .client(mockOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
