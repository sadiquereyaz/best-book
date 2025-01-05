package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.model.Order
import com.nabssam.bestbook.domain.model.OrderItem
import com.nabssam.bestbook.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow


interface OrderRepository {
    suspend fun placeOrder(items: List<OrderItem>, totalAmount: Double): Order
    suspend fun cancelOrder(orderId: String)
    suspend fun getOrderById(orderId: String): Order?
    fun getOrders(): Flow<List<Order>>
    fun trackOrder(orderId: String): Flow<OrderStatus>
}
