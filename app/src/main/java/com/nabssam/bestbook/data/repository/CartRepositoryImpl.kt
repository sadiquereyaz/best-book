package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.mapper.CartMapper
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.dto.AddToCartRequest
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.data.remote.dto.RemoveRequest
import com.nabssam.bestbook.data.remote.dto.UpdateQuantityRequest
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApiService: CartApiService,
    private val cartMapper: CartMapper,
    private val cartDao: CartDao // Inject Room DAO
) : CartRepository {

    override suspend fun fetchCartItems(): Result<List<CartItem>> =
        try {
            val response = cartApiService.fetchAll()
            //Log.d("CART_REPO", "cart response $response")
            if (response.isSuccessful) {
                // Log.d("CART_REPO", "response body: ${response.body()}")
                response.body()?.data?.cartItems?.let { items ->
                    cartDao.clearCart() // Clear previous data
//                    items.forEach { cartDao.upsertLocalCart(CartItemEntity(it.product._id, it.quantity)) }
                    Result.success(items.map {
                        cartDao.upsertLocalCart(productId = it.product._id, count = it.quantity, type = it.productType)
                        cartMapper.dtoToDomain(it)
                    })
                } ?: Result.failure(Exception("No data found"))
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e.localizedMessage ?: "An error occurred"))
        }


    override suspend fun updateQuantity(productId: String, quantity: Int, type: ProductType): Flow<Resource<String>> = flow {
            emit(Resource.Loading()) // Emit loading state
            if (quantity < 1) {
//                Log.d("CART_REPO", "updateQuantity: $productId $quantity")

                // Call removeProductFromCart and handle its Flow
                remove(productId = productId, type = type).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            cartDao.removeCartItem(productId = productId, type = type)
                            emit(Resource.Success(data = null, message = "Item removed successfully")) // Emit success from removeProductFromCart
                        }

                        is Resource.Error -> {
                            emit(Resource.Error(resource.message)) // Emit error from removeProductFromCart
                        }

                        is Resource.Loading -> {
                            // Optionally handle loading state if needed
                        }
                    }
                }
            } else {
                // Handle the case where quantity is >= 1 (e.g., update quantity in the cart)
                try {
                    val response = cartApiService.updateQuantity(UpdateQuantityRequest(productId, quantity, type))
                    if (response.isSuccessful) {
//                        updateCartCountInPreferences(quantity)
                        cartDao.updateQuantity(productId = productId, newQuantity = quantity, type = type)
                        emit(Resource.Success(data = null, message = "Quantity updated successfully")
                        )
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
                }
            }
        }

    override suspend fun addProductToCart(
        productType: ProductType,
        productId: String
    ): Result<String?> {
        //Log.d("CART_REPO", "adding productId: $productId")
        return try {
            val response = cartApiService.add(
                request = AddToCartRequest(bookId = productId, productType = productType, quantity = 1)
            )
            if (response.isSuccessful) {
                cartDao.upsertLocalCart(productId = productId, count = 1, type = productType)
//                fetchCartItems()
                Result.success("Item added successfully")
            } else {
                Result.failure(Exception("An error occurred: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("CART_REPO", "error: ${e.message}")
            Result.failure(Exception(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun remove(productId: String, type: ProductType): Flow<Resource<String?>> = flow {
            emit(Resource.Loading())
            try {
                Log.d("CART_REPO", "removing productId: $productId")
                val response = cartApiService.removeProductFromCart(RemoveRequest(productId = productId, productType = type))
                if (response.isSuccessful) {
                    emit(Resource.Success(data = null, message = "Deleted successfully"))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }
        }

    override suspend fun clearCart(userId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = cartApiService.clearCart(userId)
            if (response.isSuccessful) {
                cartDao.clearCart()
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    /**
     * Fetches the latest cart items count and updates it in AppPreferences
     */
    private suspend fun updateCartCountInPreferences(arithCount: Int) {
//        fetchCartItems().collect { resource ->
//            if (resource is Resource.Success) {
//                val totalItems = resource.data?.sumOf { it.quantity } ?: 0
        Log.d("CART_REPO", "arithCount: $arithCount")
//        val totalItems1 :Int = appPreferences.cartItemCount.first() + arithCount
//        appPreferences.updateCartItemCount(totalItems1)
//        Log.d("CART_REPO", "Cart count updated: $totalItems1")
        /*appPreferences.cartItemCount.collect { currentCount ->
            val totalItems = currentCount + arithCount
            if (totalItems >= 0) {
                appPreferences.updateCartItemCount(totalItems)
                Log.d("CART_REPO", "Cart count updated: $totalItems")
            }
        }*/
    }
}


