package com.nabssam.bestbook.utils

import android.util.Log
import androidx.navigation.NavController
import com.nabssam.bestbook.data.local.entity.CartItemEntity
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

fun List<CartItemEntity>.totalCartPrice(): Double {
    var total = 0.0
    forEach {
        total += it.quantity * it.price
    }
    return BigDecimal(total).setScale(1, RoundingMode.HALF_UP).toDouble()
}


fun List<CartItemEntity>.totalDiscountPercent(): Double {
    var originalTotal = 0.0
    var discountedTotal = 0.0

    forEach {
        val originalPriceForItem = it.quantity * it.price
        val discountedPriceForItem = originalPriceForItem * (1 - it.disPer / 100.0)

        originalTotal += originalPriceForItem
        discountedTotal += discountedPriceForItem
    }

    // Avoid division by zero, return 0% if no items
    return if (originalTotal == 0.0) {
        0.0
    } else {
        val discountPercent = ((originalTotal - discountedTotal) / originalTotal) * 100.0
        BigDecimal(discountPercent).setScale(1, RoundingMode.HALF_UP).toDouble()
    }
}

fun List<CartItemEntity>.totalDiscountAmount(): Double {
    var discountAmount = 0.0

    forEach {
        val originalPriceForItem = it.quantity * it.price
        val discountedPriceForItem = originalPriceForItem * (1 - it.disPer / 100.0)
        discountAmount += (originalPriceForItem - discountedPriceForItem)
    }

    return BigDecimal(discountAmount).setScale(1, RoundingMode.HALF_UP).toDouble()
}

// percent value calculator ex: (1.0% of 10.0) use: (1.0).percentOf(10.0) = 0.1
// Calculate percentage for Double values
fun Double.percentOf(value: Double): Double {
    return (this / 100) * value // Compute the percentage directly
}


fun Int.percentOf(value: Int): Int {
    return  (this / 100.0).times(value) // Convert `this` to a percentage and multiply
        .toBigDecimal()             // Convert result to BigDecimal
        .setScale(0, RoundingMode.HALF_UP) // Round to the nearest integer
        .toInt()                    // Convert back to Int
}


fun List<CartItemEntity>.totalItem(): Int {
    var count = 0

    forEach {
        count += it.quantity
    }

    return count
}
