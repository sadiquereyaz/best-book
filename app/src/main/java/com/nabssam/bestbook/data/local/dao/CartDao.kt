package com.nabssam.bestbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.remote.dto.ProductType
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Transaction
    suspend fun upsertLocalCart(productId: String, type: ProductType, count: Int) {
        val existingQuantity = getQuantity(productId, type)
        if (existingQuantity != null) {
            updateQuantity(productId, type, existingQuantity + count)
        } else {
            insertCartItem(CartItemEntity(productId = productId, quantity = count, type = type))
        }
    }

    @Query("SELECT quantity FROM cart_items WHERE productId = :productId AND type = :type")
    suspend fun getQuantity(productId: String, type: ProductType): Int?

    @Query("UPDATE cart_items SET quantity = :newQuantity WHERE productId = :productId AND type = :type")
    suspend fun updateQuantity(productId: String, type: ProductType, newQuantity: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Query("SELECT SUM(quantity) FROM cart_items")
     fun getTotalCartCount(): Flow<Int>

    @Query("DELETE FROM cart_items WHERE productId = :productId AND type = :type")
    suspend fun removeCartItem(productId : String, type: ProductType)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()



/*
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

    */

}

/*
INSERT INTO cart_items (productId, quantity, price, disPer, name, coverImage, inStock) VALUES
('product1', 1, 60, 10, 'Name 1', 'https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg', 1),
('product2', 2, 290, 5, 'Title 2', 'https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg', 1),
('product3', 1, 120, 15, 'Product 1', 'https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg', 1)
*/