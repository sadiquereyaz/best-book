package com.nabssam.bestbook.di.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.nabssam.bestbook.data.local.AppDatabase
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import com.nabssam.bestbook.data.repository.auth.UserPreferencesTokenStorage
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import com.nabssam.bestbook.presentation.ui.book.ebook.PDFDownloadStatusHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    private const val DATABASE_NAME = "bestbook_db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // Replace with migration if needed
            .build()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("user_preferences") }
        )
    }

    @Provides
    @Singleton
    fun provideTokenStorage(userPreferences: UserPreferencesRepository): TokenStorage {
        return UserPreferencesTokenStorage(userPreferences)
    }

    @Provides
    @Singleton
    fun providePdfStorage(@ApplicationContext context: Context): PDFDownloadStatusHelper {
        return PDFDownloadStatusHelper(context)
    }
}