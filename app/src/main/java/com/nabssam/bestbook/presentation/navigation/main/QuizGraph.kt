package com.nabssam.bestbook.presentation.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.ui.quiz.AllQuizListRoute
import com.nabssam.bestbook.presentation.ui.quiz.AllQuizListScreen
import com.nabssam.bestbook.presentation.ui.quiz.QuizSubjectScreen
import com.nabssam.bestbook.presentation.ui.quiz.MCQScreen
import com.nabssam.bestbook.presentation.navigation.Route


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