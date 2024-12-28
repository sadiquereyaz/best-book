package com.imageclasses.imageclasses.ui.feature.orderConfirmScreen

import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
//    showPaymentStatus: (paymentId: String?) -> Unit
){
    TabRow(
        selectedTabIndex = 0
    ) { }

}

@Composable
fun PaymentStatusDialog(
    modifier: Modifier = Modifier
){

}