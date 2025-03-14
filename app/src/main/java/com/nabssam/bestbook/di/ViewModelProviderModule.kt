package com.nabssam.bestbook.di

import com.nabssam.bestbook.data.mapper.AddressMapper
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.data.remote.api.AddressApiService
import com.nabssam.bestbook.data.remote.api.OrderApiService
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.domain.usecase.PlaceOrderUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllTargetUC
import com.nabssam.bestbook.domain.usecase.book.GetBookByIdUC
import com.nabssam.bestbook.domain.usecase.book.SearchProductsUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetUserTargetsUC
import com.nabssam.bestbook.domain.repository.AddressRepository
import com.nabssam.bestbook.data.repository.AddressRepositoryImpl
import com.nabssam.bestbook.presentation.ui.book.ebook.EbookRepository
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
object ViewModelProviderModule {

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
    fun provideOrderRepository(): OrderRepositoryMain {
        return OrderRepositoryMain()
    }
    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepositoryImpl {
        return ChatRepositoryImpl( )
    }

    @Provides
    @Singleton
    fun provideAddressRepository(
        addressApiService: AddressApiService,
        addressMapper: AddressMapper
    ): AddressRepository {
        return AddressRepositoryImpl(addressApiService, addressMapper)
    }
    @Provides
    @Singleton
    fun provideEbookRepository(
        api: OrderApiService,
        mapper: BookMapper
    ): EbookRepository {
        return EbookRepositoryImp(api, mapper)
    }
}