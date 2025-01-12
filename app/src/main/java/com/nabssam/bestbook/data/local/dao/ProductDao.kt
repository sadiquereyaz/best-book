package com.nabssam.bestbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nabssam.bestbook.data.local.entity.BookEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: BookEntity)

    @Query("DELETE FROM products")
    suspend fun clearProducts()


    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<BookEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM products WHERE id = :productId)")
    suspend fun exists(productId: String): Boolean

}