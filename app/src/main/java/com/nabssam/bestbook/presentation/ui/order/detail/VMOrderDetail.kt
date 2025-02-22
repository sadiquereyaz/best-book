package com.nabssam.bestbook.presentation.ui.order.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMOrderDetail @Inject constructor(
    private val orderRepository: OrderRepositoryMain,
    private val chatRepository: ChatRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderId: String = checkNotNull(savedStateHandle["orderId"])
    private val _uiState: MutableStateFlow<UiStateOrderDetail> =
        MutableStateFlow(UiStateOrderDetail.Loading)
    val uiState: StateFlow<UiStateOrderDetail> = _uiState.asStateFlow()

    init {
        fetchOrderDetail(orderId)
    }
    fun onEvent(event: EventOrderDetail) {
        when (event) {
            EventOrderDetail.Initialise -> fetchOrderDetail(orderId)
            is EventOrderDetail.SendMessage -> sendMessage(event.message)
            is EventOrderDetail.RetryMessage -> retryMessage(event.messageId)
            EventOrderDetail.DismissError -> dismissError()
            else -> Unit
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            val currentState = _uiState.value as? UiStateOrderDetail.Success ?: return@launch
            val newMessage = ChatMessage(
                message = message,
                isFromUser = true,
                timestamp = getCurrentTime(),
                status = ChatMessageStatus.Sending
            )

            // Update UI immediately with sending status
            _uiState.emit(currentState.copy(
                chatState = currentState.chatState.copy(
                    messages = currentState.chatState.messages + newMessage
                )
            ))

            try {
                // Send message to backend
                val response = chatRepository.sendMessage(orderId, message)

                // Update message status to sent
                updateMessageStatus(newMessage.id, ChatMessageStatus.Sent)

                // Add response from support if any
                if (response.supportReply != null) {
                    val supportMessage = ChatMessage(
                        message = response.supportReply,
                        isFromUser = false,
                        timestamp = getCurrentTime()
                    )
                    addMessage(supportMessage)
                }
            } catch (e: Exception) {
                updateMessageStatus(newMessage.id, ChatMessageStatus.Failed)
                updateError("Failed to send message. Please try again.")
            }
        }
    }

    private fun retryMessage(messageId: String) {
        viewModelScope.launch {
            val currentState = _uiState.value as? UiStateOrderDetail.Success ?: return@launch
            val message = currentState.chatState.messages.find { it.id == messageId } ?: return@launch

            // Update status to sending
            updateMessageStatus(messageId, ChatMessageStatus.Sending)

            try {
                // Retry sending message
                val response = chatRepository.sendMessage(orderId, message.message)
                updateMessageStatus(messageId, ChatMessageStatus.Sent)

                // Add support response if any
                if (response.supportReply != null) {
                    val supportMessage = ChatMessage(
                        message = response.supportReply,
                        isFromUser = false,
                        timestamp = getCurrentTime()
                    )
                    addMessage(supportMessage)
                }
            } catch (e: Exception) {
                updateMessageStatus(messageId, ChatMessageStatus.Failed)
                updateError("Failed to send message. Please try again.")
            }
        }
    }

    private suspend fun updateMessageStatus(messageId: String, status: ChatMessageStatus) {
        val currentState = _uiState.value as? UiStateOrderDetail.Success ?: return
        val updatedMessages = currentState.chatState.messages.map { message ->
            if (message.id == messageId) message.copy(status = status) else message
        }
        _uiState.emit(currentState.copy(
            chatState = currentState.chatState.copy(messages = updatedMessages)
        ))
    }

    private suspend fun addMessage(message: ChatMessage) {
        val currentState = _uiState.value as? UiStateOrderDetail.Success ?: return
        _uiState.emit(currentState.copy(
            chatState = currentState.chatState.copy(
                messages = currentState.chatState.messages + message
            )
        ))
    }

    private suspend fun updateError(error: String?) {
        val currentState = _uiState.value as? UiStateOrderDetail.Success ?: return
        _uiState.emit(currentState.copy(
            chatState = currentState.chatState.copy(error = error)
        ))
    }

    private fun dismissError() {
        viewModelScope.launch {
            updateError(null)
        }
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    }

    private fun fetchOrderDetail(orderId: String) {
        viewModelScope.launch {
            _uiState.emit(UiStateOrderDetail.Loading)
            println("orderId : $orderId")
            try {
                val orderDetail = orderRepository.getOrderById(orderId = orderId)
                _uiState.emit(UiStateOrderDetail.Success(orderDetail))
            } catch (e: Exception) {
                _uiState.emit(UiStateOrderDetail.Error)
            }
        }
    }
}