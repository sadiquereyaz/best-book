package com.nabssam.bestbook.presentation.ui.order.detail
sealed class EventOrderDetail {
    data object Initialise : EventOrderDetail()
    data object ReturnClicked : EventOrderDetail()
    data object ChatClicked : EventOrderDetail()
    data object RateClicked : EventOrderDetail()
    data class SendMessage(val message: String) : EventOrderDetail()
    data class RetryMessage(val messageId: String) : EventOrderDetail()
    data object DismissError : EventOrderDetail()
}