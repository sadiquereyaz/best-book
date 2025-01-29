package com.nabssam.bestbook.di

import android.content.Context
import com.nabssam.bestbook.data.repository.PdfEncryptionManager
import com.nabssam.bestbook.data.repository.PdfRepositoryImpl
import com.nabssam.bestbook.domain.repository.PdfRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PdfModule {
    @Provides
    @Singleton
    fun providePdfEncryptionManager(
        @ApplicationContext context: Context
    ): PdfEncryptionManager {
        return PdfEncryptionManager(context)
    }

    @Provides
    @Singleton
    fun providePdfRepository(
        @Auth okHttpClient: OkHttpClient,
        pdfEncryptionManager: PdfEncryptionManager,
        @ApplicationContext context: Context
    ): PdfRepository {
        return PdfRepositoryImpl(okHttpClient, pdfEncryptionManager, context)
    }
}
