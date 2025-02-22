package com.nabssam.bestbook.di.remote

import com.nabssam.bestbook.data.remote.client.AuthenticatedOkHttpClient
import com.nabssam.bestbook.data.remote.interceptors.MockInterceptor
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import com.nabssam.bestbook.di.Auth
import com.nabssam.bestbook.di.Mock
import com.nabssam.bestbook.di.Pdf
import com.nabssam.bestbook.di.UnAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import javax.inject.Qualifier
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

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
