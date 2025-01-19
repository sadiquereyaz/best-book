package com.nabssam.bestbook.presentation.ui.order.detail

import android.util.Log
import com.nabssam.bestbook.utils.DummyData
import javax.inject.Inject

class OrderRepositoryMain(

) {
    fun getOrderById(orderId: String): OrderDetail {
        Log.d("OrderRepository", "Fetching order detail for order ID: $orderId")
        return DummyData.dummyOrderDetail()
    }
}
