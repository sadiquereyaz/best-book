package com.nabssam.bestbook.di.remote

import com.nabssam.bestbook.data.remote.client.AuthenticatedOkHttpClient
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    @Provides
    @Singleton
    @UnAuth
    fun provideUnAuthOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(tokenStorage: TokenStorage, authManager: AuthManager): OkHttpClient {
        return AuthenticatedOkHttpClient(tokenStorage, authManager).create()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnAuth
