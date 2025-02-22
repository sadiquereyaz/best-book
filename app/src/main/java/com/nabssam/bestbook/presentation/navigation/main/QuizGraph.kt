package com.nabssam.bestbook.presentation.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.contest.ContestScreen
import com.nabssam.bestbook.presentation.ui.quiz.AllQuizListRoute
import com.nabssam.bestbook.presentation.ui.quiz.AllQuizListScreen
import com.nabssam.bestbook.presentation.ui.quiz.MCQScreen
import com.nabssam.bestbook.presentation.ui.quiz.QuizSubjectScreen


fun NavGraphBuilder.quizGraph(navController: NavController) {
    navigation<Route.QuizGraph>(
        startDestination = Route.Contest(title = "XI-Entrance Mock Test 3", id = "default")
    ) {

        composable<Route.Contest> { backStackEntry ->
            val routeObj: Route.Contest = backStackEntry.toRoute()
            ContestScreen()
        }
        composable<AllQuizListRoute> { backStackEntry ->
            val routeObj: AllQuizListRoute = backStackEntry.toRoute()
            AllQuizListScreen(
                examId = /*routeObj.examId*/ backStackEntry.destination.route.toString() + "\n" + AllQuizListRoute(
                    "jkh"
                ).toString(),
            )
        }
        composable<Route.MCQRoute> { backStackEntry ->
            MCQScreen(
//            quizId = routeObj.quizId ,
            )
        }
        composable<Route.QuizSubjectRoute> { backStackEntry ->
            QuizSubjectScreen(
//            quizId = routeObj.quizId,
//            categoryId = routeObj.categoryId,
                moveToMCQ = {
                    navController.navigate(Route.MCQRoute())

                }
            )
        }
    }
}