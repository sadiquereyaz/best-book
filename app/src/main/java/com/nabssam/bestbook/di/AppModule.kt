package com.nabssam.bestbook.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.nabssam.bestbook.data.remote.interceptors.MockInterceptor
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import com.nabssam.bestbook.data.repository.auth.UserPreferencesTokenStorage
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import com.nabssam.bestbook.utils.DispatcherProvider
import com.nabssam.bestbook.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override val main = Dispatchers.Main
            override val io = Dispatchers.IO
            override val default = Dispatchers.Default
            override val unconfined = Dispatchers.Unconfined
        }
    }

    @Provides
    @Singleton
    fun provideMockInterceptor(@ApplicationContext context: Context): MockInterceptor {
        return MockInterceptor(context)
    }


    @Provides
    @Singleton
    @ApplicationScope
    fun providesApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }


    // Add ApplicationScope qualifier
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApplicationScope
}