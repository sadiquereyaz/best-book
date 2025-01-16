package com.nabssam.bestbook.di.local

import com.nabssam.bestbook.data.local.AppDatabase
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule{


    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    /* @Provides
     @Singleton
     fun provideUserDao(appDatabase: AppDatabase): UserDao {
         return appDatabase.userDao()
     }
 
     @Provides
     @Singleton
     fun provideOrderDao(appDatabase: AppDatabase): OrderDao {
         return appDatabase.orderDao()
     }*/
}