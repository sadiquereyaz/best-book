package com.nabssam.bestbook.domain.usecase

import com.nabssam.bestbook.domain.model.Order
import com.nabssam.bestbook.domain.model.OrderItem
import com.nabssam.bestbook.domain.model.OrderStatus
import com.nabssam.bestbook.domain.repository.OrderRepository
import com.nabssam.bestbook.utils.ValidationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(): Order {

        val totalAmount = /*cartItems.sumOf { it.quantity * it.book.price }*/ 0

        val order =
            orderRepository.placeOrder(/*cartItems*/listOf(OrderItem("sd", "hdfid", 3, 43.3)),  // TODO: convert cartItem to order item
                totalAmount.toDouble()
            )

        return order
    }
}

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<List<Order>> {
        return repository.getOrders()
    }
}

class CancelOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId: String) {
        validateOrderId(orderId)
        repository.cancelOrder(orderId)
    }

    private fun validateOrderId(orderId: String) {
        if (orderId.isBlank()) {
            throw ValidationException("Order ID cannot be empty")
        }
    }
}

class GetOrderDetailsUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId: String): Order {
        validateOrderId(orderId)
        return repository.getOrderById(orderId)
            ?: throw ValidationException("Order not found")
    }

    private fun validateOrderId(orderId: String) {
        if (orderId.isBlank()) {
            throw ValidationException("Order ID cannot be empty")
        }
    }
}

class TrackOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    fun invoke(orderId: String): Flow<OrderStatus> {
        validateOrderId(orderId)
        return repository.trackOrder(orderId)
    }

    private fun validateOrderId(orderId: String) {
        if (orderId.isBlank()) {
            throw ValidationException("Order ID cannot be empty")
        }
    }
}