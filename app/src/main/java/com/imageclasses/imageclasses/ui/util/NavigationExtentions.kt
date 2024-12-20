package com.imageclasses.imageclasses.ui.util

import android.util.Log
import androidx.navigation.NavController

fun NavController.safeNavigate(route: String) {
    try {
        navigate(route)
    } catch (e: IllegalArgumentException) {
        Log.e("Navigation", "Failed to navigate to $route", e)
    }
}
