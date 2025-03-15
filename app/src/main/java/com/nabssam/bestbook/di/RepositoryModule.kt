package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.repository.BannerRepoImp
import com.nabssam.bestbook.data.repository.BookRepositoryImpl
import com.nabssam.bestbook.data.repository.CartRepositoryImpl
import com.nabssam.bestbook.data.repository.ExamRepositoryImpl
import com.nabssam.bestbook.data.repository.OrderRepositoryImpl
import com.nabssam.bestbook.data.repository.PyqRepoImp
import com.nabssam.bestbook.data.repository.UserDataStoreRepoImpl
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.ExamRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.repository.PyqRepository
import com.nabssam.bestbook.domain.repository.UserDataStoreRepository
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
    abstract fun bindUserPref(userDataStoreRepoImpl: UserDataStoreRepoImpl): UserDataStoreRepository

    @Binds
    @Singleton
    abstract fun bindExamRepository(examRepositoryImpl: ExamRepositoryImpl): ExamRepository

    @Binds
    @Singleton
    abstract fun bindPyqRepository(pyqRepositoryImpl: PyqRepoImp): PyqRepository

}