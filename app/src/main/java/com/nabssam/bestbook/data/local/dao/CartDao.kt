package com.nabssam.bestbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {


    @Query("SELECT SUM(quantity) FROM cart_items")
     fun getTotalCartCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLocalCart(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun removeCartItem(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    *//* @Query("DELETE FROM cart_items WHERE productId = :productId")
     suspend fun delete(productId: String)*//*

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItem(productId: String)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: String, quantity: Int)


    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getTotalItems(): Flow<Int?>

    // Check if product exists in cart
    @Query("SELECT EXISTS(SELECT * FROM cart_items WHERE productId = :productId)")
    suspend fun isProductInCart(productId: String): Boolean

    // Get specific cart item
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItemById(productId: String): CartItemEntity?


    @Query(
        "INSERT INTO cart_items (productId, quantity, price, disPer, name, coverImage, inStock) VALUES\n" +
                "('product1', 2, 100, 10, 'Product 1', 'image1.jpg', 1),\n" +
                "('product2', 1, 50, 5, 'Product 2', 'image2.jpg', 1),\n" +
                "('product3', 3, 150, 15, 'Product 3', 'image3.jpg', 1),\n" +
                "('product4', 2, 80, 8, 'Product 4', 'image4.jpg', 1);"
    )
    suspend fun insertDummy()*/
}

/*
INSERT INTO cart_items (productId, quantity, price, disPer, name, coverImage, inStock) VALUES
('product1', 1, 60, 10, 'Name 1', 'https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg', 1),
('product2', 2, 290, 5, 'Title 2', 'https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg', 1),
('product3', 1, 120, 15, 'Product 1', 'https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg', 1)
*/