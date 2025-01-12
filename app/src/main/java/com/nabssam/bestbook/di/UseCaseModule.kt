package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.repository.LocalCartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.usecase.PlaceOrderUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllCategoryUseCase
import com.nabssam.bestbook.domain.usecase.book.GetProductDetailsUseCase
import com.nabssam.bestbook.domain.usecase.book.SearchProductsUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetAllCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetTargetExamUseCase
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
    ): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(repository)
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
    ): GetAllCategoryUseCase {
        return GetAllCategoryUseCase(repository)
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
        userPreferencesRepository: UserPreferencesRepository
    ): GetTargetExamUseCase {
        return GetTargetExamUseCase(userPreferencesRepository)
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