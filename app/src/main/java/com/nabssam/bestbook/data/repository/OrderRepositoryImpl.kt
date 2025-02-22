package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.domain.model.Order
import com.nabssam.bestbook.domain.model.OrderItem
import com.nabssam.bestbook.domain.model.OrderStatus
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.utils.ValidationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.UUID

class OrderRepositoryImpl : OrderRepository {

    private val orders = mutableListOf<Order>()
    private val orderStatusFlow = MutableStateFlow<Map<String, OrderStatus>>(emptyMap())

    override suspend fun placeOrder(items: List<OrderItem>, totalAmount: Double): Order {
        val orderId = UUID.randomUUID().toString()
        val newOrder = Order(
            id = orderId,
            items = items,
            totalAmount = totalAmount,
            orderDate = Date(),
            status = OrderStatus.PENDING
        )
        orders.add(newOrder)
        updateOrderStatus(orderId, OrderStatus.PENDING)
        return newOrder
    }

    override suspend fun cancelOrder(orderId: String) {
        val order = orders.find { it.id == orderId }
        order?.let {
            orders.remove(it)
            updateOrderStatus(orderId, OrderStatus.CANCELLED)
        }
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return orders.find { it.id == orderId }
    }

    override fun getOrders(): Flow<List<Order>> {
        return orderStatusFlow.map { statusMap ->
            orders.map { order ->
                order.copy(status = statusMap[order.id] ?: order.status)
            }
        }
    }

    override fun trackOrder(orderId: String): Flow<OrderStatus> {
        return orderStatusFlow.map { statusMap ->
            statusMap[orderId] ?: throw ValidationException("Order not found")
        }
    }

    private fun updateOrderStatus(orderId: String, status: OrderStatus) {
        val currentMap = orderStatusFlow.value.toMutableMap()
        currentMap[orderId] = status
        orderStatusFlow.value = currentMap
    }
}