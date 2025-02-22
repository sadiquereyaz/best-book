package com.nabssam.bestbook.di

import android.content.Context
import androidx.work.WorkManager
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    @Singleton
    fun providePdfDownloadHelper(@ApplicationContext context: Context): PDFDownloadStatusHelper {
        return PDFDownloadStatusHelper(context)
    }
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}
