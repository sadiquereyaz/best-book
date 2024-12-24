package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.quiz.QuizRoute
import com.imageclasses.imageclasses.ui.feature.quiz.QuizScreen
import com.imageclasses.imageclasses.ui.feature.quizCategory.QuizCategoryRoute
import com.imageclasses.imageclasses.ui.feature.quizCategory.QuizCategoryScreen

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
