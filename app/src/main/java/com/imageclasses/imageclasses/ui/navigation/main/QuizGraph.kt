package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.quiz.AllQuizListRoute
import com.imageclasses.imageclasses.ui.feature.quiz.AllQuizListScreen
import com.imageclasses.imageclasses.ui.feature.quiz.QuizSubjectScreen
import com.imageclasses.imageclasses.ui.feature.quiz.MCQScreen
import com.imageclasses.imageclasses.ui.navigation.Route


fun NavGraphBuilder.quizGraph(navController: NavController) {

    composable<AllQuizListRoute> { backStackEntry ->
        val routeObj: AllQuizListRoute = backStackEntry.toRoute()
        AllQuizListScreen(
            examId = /*routeObj.examId*/ backStackEntry.destination.route.toString()+ "\n" + AllQuizListRoute("jkh").toString(),
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
            moveToMCQ = {navController.navigate(Route.MCQRoute())

            }
        )
    }
}