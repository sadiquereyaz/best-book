package com.nabssam.bestbook.utils

import android.util.Log
import androidx.navigation.NavController
import com.nabssam.bestbook.domain.model.CartModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NavController.safeNavigate(route: String) {
    try {
        navigate(route)
    } catch (e: IllegalArgumentException) {
        Log.e("Navigation", "Failed to navigate to $route", e)
    }
}

fun NavController.safeNavigate1(route: Any) {
    try {
        navigate(route)
    } catch (e: IllegalArgumentException) {
        Log.e("Navigation", "Failed to navigate to $route", e)
    }
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Double.formatPrice(): String {
    return String.format("$%.2f", this)
}

fun Long.toFormattedDate(): String {
    val sdf = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(this))
}


fun formatOrderDate(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun List<CartModel>.totalCartPrice(): Double {
    var total = 0.0
    forEach {
        total += it.productQuantity * it.productPrice
    }
    return BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toDouble()
}

