package com.nabssam.bestbook.di

import android.content.Context
import com.nabssam.bestbook.BuildConfig
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.api.BookApi
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import com.nabssam.bestbook.data.repository.auth.AuthInterceptor
import com.nabssam.bestbook.data.repository.auth.AuthManager
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(authInterceptor)
//            .connectTimeout(10, TimeUnit.SECONDS)   //30
//            .readTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalApi(retrofit: Retrofit): CartApiService {   //TODO: delete/rename it
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartApiClaude(retrofit: Retrofit): CartApiServiceClaude {
        return retrofit.create(CartApiServiceClaude::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityRepository(@ApplicationContext context: Context): NetworkConnectivityRepository {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideExamApi(retrofit: Retrofit): ExamApi {
        return retrofit.create(ExamApi::class.java)
    }

    @Provides
    fun provideAuthInterceptor(userPreferences: UserPreferencesRepository, authManager: AuthManager): AuthInterceptor {
        return AuthInterceptor(userPreferences, authManager)
    }

    @Provides
    @Singleton
    fun provideAuthManager(userPreferences: UserPreferencesRepository, authApiService: AuthApiService): AuthManager {
        return AuthManager(userPreferences, authApiService)
    }
}