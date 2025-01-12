package com.nabssam.bestbook.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AuthGraph : Route

    @Serializable
    data class SignIn(val title: String = "Best Book") : Route

    @Serializable
    data class SignUp(val title: String = "Sign Up") : Route

    @Serializable
    data object MainGraph : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object SubscribedQuiz : Route

    @Serializable
    data object Ebook : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class QuizRoute(val title: String = "Quiz", val quizId: Int) : Route

    @Serializable
    data class QuizSubjectRoute(val title: String = "Category",var examId:String = "") : Route
    @Serializable
    data class MCQRoute(val title: String = "Question") : Route

    @Serializable
    data class AllBookRoute(val title: String = "Book Store", val targetExam: String) : Route

    @Serializable
    data class BookDetailRoute(val id: String, val title: String = "Book Detail") : Route

    @Serializable
    data class AllReviewRoute(val bookId: Int, val title: String= "Review") : Route

    @Serializable
    data class CartRoute(val title: String = "Your Cart") : Route

    @Serializable
    data class AddressRoute(val title: String = "Delivery Address") : Route

    @Serializable
    data class OrderRoute(val orderId: Int = 0, val title: String = "Your Order") : Route


    @Serializable
    data object PaymentDialog : Route
}

enum class TopLevelDestination(
    val label: String,
    val route: Route,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {

    HOME(
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        label = "Home",
        route = Route.Home
    ),
    QUIZ(
        selectedIcon = Icons.Filled.DateRange,
        unSelectedIcon = Icons.Outlined.DateRange,
        label = "Quiz",
        route = Route.SubscribedQuiz
    ),
    EBOOK(
        selectedIcon = Icons.Filled.Edit,
        unSelectedIcon = Icons.Outlined.Edit,
        label = "E-book",
        route = Route.Ebook
    ),
    PROFILE(
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
        label = "Profile",
        route = Route.Profile
    ),
    ;

    companion object {
        val START_DESTINATION = HOME

        fun fromNavDestination(destination: NavDestination?): TopLevelDestination {
            return entries.find { dest ->
                destination?.hierarchy?.any {
                    it.hasRoute(dest.route::class)
                } == true
            } ?: START_DESTINATION
        }

        fun NavDestination.isTopLevel(): Boolean {
            return entries.any {
                hasRoute(it.route::class)
            }
        }
    }
}