import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.nabssam.bestbook.presentation.navigation.Route

fun NavController.bottomNavigationLogic(route: Route) {
    navigate(route = route) {
        popUpTo(Route.Home) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}


// Extension functions for common navigation patterns
/* For your bottom navigation, use navigateAndPopUpToStart instead of the current bottomNavigationLogic:

bottomBar.onItemSelected { route ->
    navController.navigateAndPopUpToStart(route)
}
 */
fun NavController.navigateAndPopUpToStart(route: Route) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

// For nested navigation with back stack handling
/*onNavigateToBook = { bookId ->
    navController.navigateWithinGraph(Route.BookDetailRoute(bookId))
}*/
fun NavController.navigateWithinGraph(route: Route) {
    navigate(route) {
        launchSingleTop = true
    }
}

// For modal-style navigation (e.g., dialogs, bottom sheets)
/* In OrderListScreen
onOrderClick = { orderId ->
    navController.navigateModal(Route.OrderDetailRoute(orderId, title))
}*/
fun NavController.navigateModal(route: Route) {
    navigate(route) {
        launchSingleTop = true
        popUpTo(currentBackStackEntry?.destination?.route ?: return@navigate) {
            saveState = true
        }
    }
}

// For clearing back stack and starting fresh
fun NavController.navigateAndClearBackstack(route: Route) {
    navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

// For handling deep links
fun NavController.handleDeepLink(deepLink: String) {
    when {
        deepLink.contains("book/") -> {
            val bookId = deepLink.substringAfter("book/")
            navigate(Route.BookDetailRoute(bookId))
        }
        deepLink.contains("quiz/") -> {
            val quizId = deepLink.substringAfter("quiz/").toIntOrNull() ?: return
            navigate(Route.QuizSubjectRoute())
        }
        deepLink.contains("order/") -> {
            val orderId = deepLink.substringAfter("order/")
            navigate(Route.OrderDetailRoute(orderId = orderId, title = "Order Details"))
        }
    }
}

// Result handling navigation
fun NavController.navigateForResult(route: Route, onResult: (Bundle) -> Unit) {
    val resultKey = "navigation_result"
    currentBackStackEntry?.savedStateHandle?.let { handle ->
        handle[resultKey] = null
        navigate(route)

        // Set up result listener
        handle.getLiveData<Bundle>(resultKey).observeForever { result ->
            if (result != null) {
                onResult(result)
                handle[resultKey] = null
            }
        }
    }
}

// Helper function to send back navigation result
fun NavController.setNavigationResult(result: Bundle) {
    val resultKey = "navigation_result"
    previousBackStackEntry?.savedStateHandle?.set(resultKey, result)
    popBackStack()
}