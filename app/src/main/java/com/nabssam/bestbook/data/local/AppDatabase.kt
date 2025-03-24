package com.nabssam.bestbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.dao.CategoryDao
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.local.entity.BookEntity
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.local.entity.CategoryEntity

@Database(
    entities = [BookEntity::class, CategoryEntity::class, CartItemEntity::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao
}