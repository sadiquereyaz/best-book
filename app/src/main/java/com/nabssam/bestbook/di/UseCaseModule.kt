package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.repository.LocalCartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.usecase.PlaceOrderUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllTargetUC
import com.nabssam.bestbook.domain.usecase.book.GetBookByIdUC
import com.nabssam.bestbook.domain.usecase.book.SearchProductsUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetAllCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetUserTargetsUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetProductDetailsUseCase(
        repository: BookRepository
    ): GetBookByIdUC {
        return GetBookByIdUC(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllBookUseCase(
        repository: BookRepository
    ): GetAllBookUseCase {
        return GetAllBookUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetAllCategoryUseCase(
        repository: BookRepository
    ): GetAllTargetUC {
        return GetAllTargetUC(repository)
    }

    @Provides
    @Singleton
    fun provideSearchProductsUseCase(
        repository: BookRepository,
        mapper: BookMapper
    ): SearchProductsUseCase {
        return SearchProductsUseCase(repository, mapper)
    }

    @Singleton
    @Provides
    fun provideGetUserFavoriteCategoryUseCase(
        userPrefRepoImpl: UserPrefRepoImpl
    ): GetUserTargetsUC {
        return GetUserTargetsUC(userPrefRepoImpl)
    }

   /* @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        repository: AuthRepository
    ): SignUpUseCase {
        return SignUpUseCase(repository)
    }*/

    @Provides
    @Singleton
    fun provideAddToCartUseCase(
        localCartRepository: LocalCartRepository,
        bookRepository: BookRepository
    ): AddToCartUseCase {
        return AddToCartUseCase(localCartRepository, bookRepository)
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(
        repository: LocalCartRepository
    ): GetAllCartItemsUseCase {
        return GetAllCartItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePlaceOrderUseCase(
        orderRepository: OrderRepository,
        localCartRepository: LocalCartRepository
    ): PlaceOrderUseCase {
        return PlaceOrderUseCase(orderRepository, localCartRepository)
    }
}