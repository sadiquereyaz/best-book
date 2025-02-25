package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.repository.BannerRepoImp
import com.nabssam.bestbook.data.repository.BookRepositoryImpl
import com.nabssam.bestbook.data.repository.CartRepositoryImpl
import com.nabssam.bestbook.data.repository.OrderRepositoryImpl
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindBannerRepository(bannerRepoImp: BannerRepoImp): BannerRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    abstract fun bindUserPref(userPrefRepoImpl: UserPrefRepoImpl): UserPreferencesRepository

}