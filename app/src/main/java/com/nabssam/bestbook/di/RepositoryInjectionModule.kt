package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.AddressMapper
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.AddressApiService
import com.nabssam.bestbook.data.remote.api.OrderApiService
import com.nabssam.bestbook.data.repository.AddressRepositoryImpl
import com.nabssam.bestbook.domain.repository.AddressRepository
import com.nabssam.bestbook.domain.repository.EbookRepository
import com.nabssam.bestbook.presentation.ui.book.ebook.EbookRepositoryImp
import com.nabssam.bestbook.presentation.ui.order.detail.ChatRepositoryImpl
import com.nabssam.bestbook.presentation.ui.order.detail.OrderRepositoryMain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryInjectionModule {

    @Provides
    @Singleton
    fun provideOrderRepository(): OrderRepositoryMain {
        return OrderRepositoryMain()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepositoryImpl {
        return ChatRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAddressRepository(
        addressApi: AddressApiService,
        addressDataMapper: AddressMapper
    ): AddressRepository {
        return AddressRepositoryImpl(addressApi, addressDataMapper)
    }

    @Provides
    @Singleton
    fun provideEbookRepository(
        orderApi: OrderApiService,
        bookDataMapper: BookMapper
    ): EbookRepository {
        return EbookRepositoryImp(orderApi, bookDataMapper)
    }

}