package com.nabssam.bestbook.data.local.dao

import com.nabssam.bestbook.data.local.entity.BookEntity

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

// First, let's create a relationship between CartItem and Product
data class CartWithProduct(
    @Embedded val cartItem: CartItemEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: BookEntity
)

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun delete(productId: String)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: String, quantity: Int)

    @Transaction
    @Query("SELECT * FROM cart_items")
    fun getCartWithProducts(): Flow<List<CartWithProduct>>

    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getTotalItems(): Flow<Int?>

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    // Check if product exists in cart
    @Query("SELECT EXISTS(SELECT * FROM cart_items WHERE productId = :productId)")
    suspend fun isProductInCart(productId: String): Boolean

    // Get specific cart item
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItem(productId: String): CartItemEntity?

    // Update quantity with safety check
    @Transaction
    suspend fun safeUpdateQuantity(productId: String, quantity: Int) {
        val cartItem = getCartItem(productId)
        if (cartItem != null && quantity > 0) {
            updateQuantity(productId, quantity)
        }
    }
}