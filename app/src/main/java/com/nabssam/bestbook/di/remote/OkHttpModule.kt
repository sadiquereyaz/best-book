package com.nabssam.bestbook.di.remote

import com.nabssam.bestbook.data.remote.client.AuthenticatedOkHttpClient
import com.nabssam.bestbook.data.remote.client.DelhiveryOkHttpClient
import com.nabssam.bestbook.data.remote.interceptors.MockInterceptor
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import com.nabssam.bestbook.di.Auth
import com.nabssam.bestbook.di.Delhivery
import com.nabssam.bestbook.di.Mock
import com.nabssam.bestbook.di.UnAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module // This annotation tells Dagger that this class is a module that provides dependencies.
@InstallIn(SingletonComponent::class)   //This tells Hilt that the dependencies provided in this module should be available in the SingletonComponent, which means they'll be available throughout the application's lifecycle.
object OkHttpModule {

    @Provides   //This annotation tells Dagger that this method provides an instance of the type it returns (Retrofit in this case).
    @Singleton  //This annotation indicates that Dagger should create only one instance of this Retrofit object and reuse it throughout the application.
    @UnAuth     //telling Dagger that this method provides a Retrofit instance that should be used when @UnAuth is requested.
    fun provideUnAuthOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(
        tokenStorage: TokenStorage,
        authManager: AuthManager
    ): OkHttpClient {
        return AuthenticatedOkHttpClient(tokenStorage, authManager).create()
    }

    @Provides
    @Singleton
    @Delhivery
    fun provideDelhiveryOkHttpClient(
        tokenStorage: TokenStorage
    ): OkHttpClient {
        return DelhiveryOkHttpClient(tokenStorage).create()
    }


    @Provides
    @Singleton
    @Mock
    fun provideMockOkHttpClient(mockInterceptor: MockInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            //.addInterceptor(loggingInterceptor)
            .addInterceptor(mockInterceptor) // Add the MockInterceptor here
            .build()
    }
}
