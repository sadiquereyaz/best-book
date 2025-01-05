package com.nabssam.bestbook.di

import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.repository.ProductRepository
import com.nabssam.bestbook.domain.usecase.PlaceOrderUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.product.GetProductDetailsUseCase
import com.nabssam.bestbook.domain.usecase.product.GetProductsUseCase
import com.nabssam.bestbook.domain.usecase.product.SearchProductsUseCase
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
        repository: ProductRepository
    ): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(
        repository: ProductRepository
    ): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchProductsUseCase(
        repository: ProductRepository
    ): SearchProductsUseCase {
        return SearchProductsUseCase(repository)
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
        productRepository: ProductRepository
    ): AddToCartUseCase {
        return AddToCartUseCase(cartRepository, productRepository)
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(
        repository: CartRepository
    ): GetCartItemsUseCase {
        return GetCartItemsUseCase(repository)
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