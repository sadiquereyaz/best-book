package com.nabssam.bestbook.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    @SerialName("userId")
    val userId: String,

   /* @SerialName("items")
    val items: List<CartItemFinal>, // Change Any to a specific data type as needed
*/
    @SerialName("totalAmount")
    val totalAmount: Double,

    @SerialName("status")
    val status: String = OrderStatusEnum.PENDING.name,

    @SerialName("shippingAddress")
    val shippingAddress: Address,

    @SerialName("paymentProvider")
    val paymentProvider: String = PaymentProviderEnum.COD.name,

    @SerialName("isPaymentDone")
    val isPaymentDone: Boolean = false
)

@Serializable
data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
)

enum class OrderStatusEnum {
    PENDING, DELIVERED, CANCELLED
}

enum class PaymentProviderEnum {
    COD, RAZORPAY, PAYPAL
}
