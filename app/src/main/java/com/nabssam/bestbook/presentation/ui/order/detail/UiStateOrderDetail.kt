package com.nabssam.bestbook.presentation.ui.order.detail

import com.nabssam.bestbook.data.remote.dto.Review
import com.nabssam.bestbook.utils.DummyData
import java.util.Date
sealed class ChatMessageStatus {
    data object Sent : ChatMessageStatus()
    data object Sending : ChatMessageStatus()
    data object Failed : ChatMessageStatus()
}

data class ChatMessage(
    val id: String = "UUID.randomUUID().toString()",
    val message: String,
    val isFromUser: Boolean,
    val timestamp: String,
    val status: ChatMessageStatus = ChatMessageStatus.Sent
)

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// UiStateOrderDetail.kt
sealed class UiStateOrderDetail {
    data object Loading : UiStateOrderDetail()
    data class Success(
        val orderDetail: OrderDetail = DummyData.dummyOrderDetail(),
        val chatState: ChatState = ChatState()
    ) : UiStateOrderDetail()
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
    PLACED, PROCESSING, SHIPPED, TRANSIT, DELIVERED, CANCELLED, REFUNDED
}
