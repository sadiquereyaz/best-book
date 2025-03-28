package com.nabssam.bestbook.utils

import com.nabssam.bestbook.domain.model.CartItem
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

fun List<CartItem>.totalCartPrice(): Double {
    var total = 0.0
    forEach {
        total += (it.quantity ?: 0) * it.price
    }
    return BigDecimal(total).setScale(1, RoundingMode.HALF_UP).toDouble()
}


fun List<CartItem>.totalDiscountPercent(): Double {
    var originalTotal = 0.0
    var discountedTotal = 0.0

    forEach {
        val originalPriceForItem = (it.quantity ?: 0) * it.price
        val discountedPriceForItem = originalPriceForItem * (1 - it.discount / 100.0)

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

fun List<CartItem>.totalDiscountAmount(): Double {
    var discountAmount = 0.0

    forEach {
        val originalPriceForItem = (it.quantity?:0).times(it.price)
        val discountedPriceForItem = originalPriceForItem * (1 - it.discount / 100.0)
        discountAmount += (originalPriceForItem - discountedPriceForItem)
    }

    return BigDecimal(discountAmount).setScale(1, RoundingMode.HALF_UP).toDouble()
}
fun List<CartItem>.totalItem(): Int {
    var count = 0
    forEach {
        count += it.quantity ?: 0
    }
    return count
}

// percent value calculator ex: (1.0% of 10.0) use: (1.0).percentOf(10.0) = 0.1
// Calculate percentage for Double values
fun Double.percentOf(value: Double) = BigDecimal((this / 100) * value).setScale(1, RoundingMode.HALF_DOWN).toDouble() // Compute the percentage directly

fun Int.percentOf(value: Int): Int {
    return  (this / 100.0).times(value) // Convert `this` to a percentage and multiply
        .toBigDecimal()             // Convert result to BigDecimal
        .setScale(0, RoundingMode.HALF_UP) // Round to the nearest integer
        .toInt()                    // Convert back to Int
}

