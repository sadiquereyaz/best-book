package com.nabssam.bestbook.domain.model

import com.nabssam.bestbook.data.local.entity.CartItemEntity

data class OrderModel(
    val orderId: String,
    val items: List<CartModel>,
    val orderStatus: String,
    val date: String,
    val shippingDetails: ShippingDetailModel
)

data class CartModel(
    val productId:Int,
    val productImage: String,
    val productName: String,
    val productPrice: Double,
    val productQuantity: Int
)

data class CartItemSK(val itemId: Int, val quantity: Int) {
    // No-argument constructor required for Firebase
    constructor() : this(0, 0)
}

data class OrderResponse(
    val orderId: String,
    val items: List<CartItemEntity>,
    val orderStatus: String,
    val date: String,
    val shippingDetails: ShippingDetailModel
) {
    constructor() : this("", emptyList(), "", "",  ShippingDetailModel())
}

data class OrdersListItem(
    val id: String,
    val status: String,
    val date: String,
    val productImage: String,
    val productName: String,
    val productDescription: String
)

data class PaymentMethodModel(val icon: Int, val methodName: String)


data class ShippingDetailModel(
    val name: String="",
    val phoneNumber: String="",
    val city: String="",
    val state: String="",
    val streetAddress: String = "",
    val houseNumber: String = ""
)