package com.nabssam.bestbook.ui.util

import android.util.Log
import androidx.navigation.NavController

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
