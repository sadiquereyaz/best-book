package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.AddressMapper
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.mapper.CartMapper
import com.nabssam.bestbook.data.mapper.ExamMapper
import com.nabssam.bestbook.data.mapper.UserMapper
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
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
    fun provideProductMapper(
        downloadStatusHelper: PDFDownloadStatusHelper
    ): BookMapper {
        return BookMapper(downloadStatusHelper)
    }

    @Provides
    @Singleton
    fun provideExamMapper(): ExamMapper {
        return ExamMapper()
    }
    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }
    @Provides
    @Singleton
    fun provideCartMapper(): CartMapper {
        return CartMapper()
    }
    @Provides
    @Singleton
    fun provideAddressMapper(): AddressMapper {
        return AddressMapper()
    }
}
