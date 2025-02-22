package com.nabssam.bestbook.presentation.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.settingsGraph(navController: NavController) {
    navigation(startDestination = "Screen.Settings.route", route = "settings") {
        composable("Screen.Settings.route") {
           /* SettingsScreen(
                onNotificationClick = { navController.navigate(Screen.Notifications.route) },
                onPrivacyClick = { navController.navigate(Screen.Privacy.route) }
            )*/
        }
        composable("Screen.Notifications.route") {
          //  NotificationsScreen()
        }
        composable("Screen.Privacy.route") {
           // PrivacyScreen()
        }
    }
}
