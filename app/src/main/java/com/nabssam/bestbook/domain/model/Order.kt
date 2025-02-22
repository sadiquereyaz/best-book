package com.nabssam.bestbook.domain.model

import java.util.Date

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val totalAmount: Double,
    val orderDate: Date,
    val status: OrderStatus
)

data class OrderItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)

enum class OrderStatus {
    PENDING,    // Order is placed but not yet processed
    CONFIRMED,  // Order is confirmed by the seller
    SHIPPED,    // Order is shipped
    DELIVERED,  // Order is delivered to the customer
    CANCELLED   // Order is cancelled
}