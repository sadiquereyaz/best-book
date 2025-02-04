package com.nabssam.bestbook.data.repository

import android.util.Log
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
) : CartRepository {

    override suspend fun fetchCartItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = cartApiService.fetchAll()
            Log.d("CART_REPO", "cart response $response")
            if (response.isSuccessful) {
                Log.d("CART_REPO", "response body: ${response.body()}")
                response.body()?.cartData?.cartItems?.let {
                    emit(Resource.Success(it.map { bookDto -> cartMapper.dtoToDomain(bookDto) }))
                } ?: emit(Resource.Error("No data found"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun updateQuantity(productId: String, quantity: Int): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading()) // Emit loading state
            if (quantity < 1) {
                Log.d("CR_I", "updateQuantity: $productId $quantity")

                // Call removeProductFromCart and handle its Flow
                removeProductFromCart(productId).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            emit(
                                Resource.Success(
                                    data = null,
                                    message = "Item removed successfully"
                                )
                            ) // Emit success from removeProductFromCart
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
                    val response =
                        cartApiService.updateQuantity(UpdateQuantityRequest(productId, quantity))
                    if (response.isSuccessful) {
                        emit(
                            Resource.Success(
                                data = null,
                                message = "Quantity updated successfully"
                            )
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
    ): Flow<Resource<String?>> = flow {
        Log.d("CART_REPO", "adding productId: $productId")
        emit(Resource.Loading())
        try {
            val response = cartApiService.add(
                request = AddToCartRequest(
                    bookId = productId,
                    productType = productType,
                    quantity = 1
                )
            )
            Log.d("CART_REPO", "Adding..${response}")
            Log.d("CART_REPO", "Adding..${response.body()}")
            if (response.isSuccessful) {
                emit(Resource.Success(data = null, message = "Item added successfully"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            Log.d("CART_REPO", "error: ${e.message}")
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun removeProductFromCart(productId: String): Flow<Resource<String?>> =
        flow {
            emit(Resource.Loading())
            try {
                Log.d("CART_REPO", "removing productId: $productId")
                val response = cartApiService.removeProductFromCart(RemoveRequest(productId))
                if (response.isSuccessful) {
                    emit(Resource.Success(data = null, message = "deleted successfully"))
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
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}
