package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.usecase.PlaceOrderUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetAllCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetTargetExamUseCase
import com.nabssam.bestbook.domain.usecase.book.GetProductDetailsUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllCategoryUseCase
import com.nabssam.bestbook.domain.usecase.book.SearchProductsUseCase
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
        cartRepository: CartRepository,
        bookRepository: BookRepository
    ): AddToCartUseCase {
        return AddToCartUseCase(cartRepository, bookRepository)
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(
        repository: CartRepository
    ): GetAllCartItemsUseCase {
        return GetAllCartItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePlaceOrderUseCase(
        orderRepository: OrderRepository,
        cartRepository: CartRepository
    ): PlaceOrderUseCase {
        return PlaceOrderUseCase(orderRepository, cartRepository)
    }
}