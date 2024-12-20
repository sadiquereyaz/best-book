package com.imageclasses.imageclasses.ui.feature.bookDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusRoute(val orderId: String)
@Serializable
data class PaymentStatusRoute(val orderId: String)

@Composable
fun OrderStatusScreen(
    modifier: Modifier = Modifier,
    showPaymentStatus: () -> Unit
){

}

@Composable
fun PaymentStatusDialog(
    modifier: Modifier = Modifier
){

}