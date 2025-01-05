package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.local.AppDatabase
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.mapper.ProductMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    @Singleton
    fun provideProductMapper(): ProductMapper {
        return ProductMapper()
    }
}
