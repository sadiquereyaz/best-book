package com.nabssam.bestbook.di

import android.content.Context
import com.nabssam.bestbook.BuildConfig
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.data.repository.auth.AuthManager
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
    @UnAuth //optional
    fun provideUnAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    @Auth
    // Authenticated client for other APIs
    fun provideAuthOkHttpClient(tokenStorage: TokenStorage, authManager: AuthManager): OkHttpClient {    // return okhttp having tokens from local storage
        return AuthenticatedOkHttpClient(tokenStorage, authManager).create()
    }

    @Provides
    @Singleton
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
    fun provideTrackingRetrofit(@Auth /*optional*/ okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)   // TODO: replace by tracking base url
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityRepository(@ApplicationContext context: Context): NetworkConnectivityRepository {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideTokenStorage(userPreferences: UserPreferencesRepository): TokenStorage {
        return UserPreferencesTokenStorage(userPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthApi( retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookApi( retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApi( retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApiClaude( retrofit: Retrofit): CartApiServiceClaude {
        return retrofit.create(CartApiServiceClaude::class.java)
    }

    @Provides
    @Singleton
    fun provideExamApi( retrofit: Retrofit): ExamApi {
        return retrofit.create(ExamApi::class.java)
    }
}


//qualifiers for different instances of same class
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnAuth


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Track
