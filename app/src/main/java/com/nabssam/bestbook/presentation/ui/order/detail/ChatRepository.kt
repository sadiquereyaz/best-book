package com.nabssam.bestbook.presentation.ui.order.detail

class ChatRepositoryImpl {
    fun sendMessage(orderId: String, message: String): ChatResponse {
        return ChatResponse(false, "I am bot")
    }
}

data class ChatResponse(
    val success: Boolean,
    val supportReply: String?
)