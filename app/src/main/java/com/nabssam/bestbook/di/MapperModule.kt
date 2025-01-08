package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.mapper.MiscMapper
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
    fun provideProductMapper(): BookMapper {
        return BookMapper()
    }
 @Provides
    @Singleton
    fun provideMiscMapper(): MiscMapper {
        return MiscMapper()
    }
}
