package com.nabssam.bestbook.di

import android.content.Context
import com.nabssam.bestbook.BuildConfig
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import com.nabssam.bestbook.data.repository.auth.AuthenticatedOkHttpClient
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import com.nabssam.bestbook.data.repository.auth.UserPreferencesTokenStorage
import com.nabssam.bestbook.domain.repository.NetworkConnectivityRepository
import com.nabssam.bestbook.presentation.ui.cart.claude.CartApiServiceClaude
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    // Unauthenticated client for auth API
    @Provides
    @Singleton
    @UnAuth
    fun provideUnAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    // Authenticated client for other APIs
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(tokenStorage: TokenStorage): AuthenticatedOkHttpClient {
        return AuthenticatedOkHttpClient(tokenStorage)
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(authenticatedClient: AuthenticatedOkHttpClient): OkHttpClient {
        return authenticatedClient.create()
    }

    // Base retrofit for auth API
    @Provides
    @Singleton
    @UnAuth
    fun provideBaseRetrofit(@UnAuth okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Authenticated retrofit for other APIs
    @Provides
    @Singleton
    @Auth
    fun provideAuthenticatedRetrofit(@Auth okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Auth API using unauthenticated client
    @Provides
    @Singleton
    fun provideAuthApi(@Auth retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }


    // Other APIs using authenticated client
    @Provides
    @Singleton
    fun provideBookApi(@UnAuth retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApi(@UnAuth retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApiClaude(@UnAuth retrofit: Retrofit): CartApiServiceClaude {
        return retrofit.create(CartApiServiceClaude::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityRepository(@ApplicationContext context: Context): NetworkConnectivityRepository {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideExamApi(@UnAuth retrofit: Retrofit): ExamApi {
        return retrofit.create(ExamApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenStorage(userPreferences: UserPreferencesRepository): TokenStorage {
        return UserPreferencesTokenStorage(userPreferences)
    }
}


//qualifiers for different instances of same class
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnAuth
