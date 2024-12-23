package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.quiz.QuizRoute
import com.imageclasses.imageclasses.ui.feature.quiz.QuizScreen
import com.imageclasses.imageclasses.ui.feature.quizCategory.QuizCategoryRoute
import com.imageclasses.imageclasses.ui.feature.quizCategory.QuizCategoryScreen
import com.imageclasses.imageclasses.ui.feature.quizList.AllQuizListRoute
import com.imageclasses.imageclasses.ui.feature.quizList.AllQuizListScreen
import kotlinx.serialization.Serializable


@Serializable
data object QuizGraphRoute
fun NavGraphBuilder.quizGraph() {
    composable<AllQuizListRoute> { backStackEntry ->
        val routeObj: AllQuizListRoute = backStackEntry.toRoute()
        AllQuizListScreen(
            examId = routeObj.examId,
        )
    }
    composable<QuizCategoryRoute> { backStackEntry ->
        val routeObj: QuizCategoryRoute = backStackEntry.toRoute()
        QuizCategoryScreen(
            quizId = routeObj.quizId,
        )
    }
    composable<QuizRoute> { backStackEntry ->
        val routeObj: QuizRoute = backStackEntry.toRoute()
        QuizScreen(
            quizId = routeObj.quizId,
            categoryId = routeObj.categoryId,
        )
    }
}