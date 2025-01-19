package com.nabssam.bestbook.presentation.ui.order.detail

import com.nabssam.bestbook.data.remote.dto.Review
import com.nabssam.bestbook.utils.DummyData
import java.util.Date

sealed class UiStateOrderDetail {
    data object Loading : UiStateOrderDetail()
    data class Success(val orderDetail: OrderDetail = DummyData.dummyOrderDetail()) :
        UiStateOrderDetail()

    data object Error : UiStateOrderDetail()
}

data class OrderDetail(
    val orderId: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Int,
    val items: String,
    val image: String,
    val status: OrderStatusMain,
    val orderDate: Date,
    val returnBefore: Date,
    val userReview: Review? = null,
    val seller: String,
)


enum class OrderStatusMain {
    PLACED, PROCESSING, SHIPPED, TRANSIT, DELIVERED, CANCELLED
}



