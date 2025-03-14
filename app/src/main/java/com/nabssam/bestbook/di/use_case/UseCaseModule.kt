package com.nabssam.bestbook.di.use_case

import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.usecase.GetAllBannerUseCase
import com.nabssam.bestbook.domain.usecase.PlaceOrderUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllTargetUC
import com.nabssam.bestbook.domain.usecase.book.GetBookByIdUC
import com.nabssam.bestbook.domain.usecase.book.SearchProductsUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.exam.GetUserTargetsUC
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

    @Provides
    @Singleton
    fun provideAddToCartUseCase(
        cartRepository: CartRepository
    ): AddToCartUseCase {
        return AddToCartUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun providePlaceOrderUseCase(
        orderRepository: OrderRepository,
    ): PlaceOrderUseCase {
        return PlaceOrderUseCase(orderRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllBannerUseCase(bannerRepository: BannerRepository) = GetAllBannerUseCase(bannerRepository)
}