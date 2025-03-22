package com.nabssam.bestbook.utils

import android.os.Build
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//@RequiresApi(Build.VERSION_CODES.O)
fun String.parseDateFromIso(): String {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return this
    else {// Parse the ISO date-time string
        try {
            val instant = Instant.parse(this)

            // Convert to LocalDate (ignoring time)
            val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()

            // Format as needed (e.g., "15 Jan 2025")
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            return localDate.format(formatter)
        } catch (e: Exception) {
            return this
        }
    }
}