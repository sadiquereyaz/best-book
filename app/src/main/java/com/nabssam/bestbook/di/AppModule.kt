package com.nabssam.bestbook.di

import android.content.Context
import com.nabssam.bestbook.data.remote.interceptors.MockInterceptor
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManagerImpl
import com.nabssam.bestbook.utils.DispatcherProvider
import com.nabssam.bestbook.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
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

    @Module
    @InstallIn(SingletonComponent::class)
    object SnackbarModule {
        @Provides
        @Singleton
        fun provideSnackbarManager(): SnackbarManager = SnackbarManagerImpl()
    }
}