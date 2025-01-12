package com.nabssam.bestbook.presentation.navigation.main

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.ui.quiz.AllQuizListRoute
import com.nabssam.bestbook.presentation.ui.quiz.AllQuizListScreen
import com.nabssam.bestbook.presentation.ui.quiz.QuizSubjectScreen
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.quiz.ExamViewModel
import com.nabssam.bestbook.presentation.ui.quiz.MCQScreen
import com.nabssam.bestbook.presentation.ui.quiz.QuizScreen


fun NavGraphBuilder.quizGraph(navController: NavController) {

    composable<AllQuizListRoute> { backStackEntry ->
        val routeObj: AllQuizListRoute = backStackEntry.toRoute()
        AllQuizListScreen(
            examId = /*routeObj.examId*/ backStackEntry.destination.route.toString() + "\n" + AllQuizListRoute(
                ""
            ).toString(),
        )
    }
    composable<Route.MCQRoute> { backStackEntry ->
        MCQScreen(
//            quizId = routeObj.quizId ,
        )
    }

    composable<Route.QuizSubjectRoute> { backStackEntry ->
        val viewModel = hiltViewModel<ExamViewModel>()
        val state by viewModel.uiState.collectAsState()

        val routeObj: Route.QuizSubjectRoute = backStackEntry.toRoute()
        Log.d("ExamViewModel", "quizGraph: ${routeObj.examId}")
        QuizSubjectScreen(
//            quizId = routeObj.quizId,
//            categoryId = routeObj.categoryId,

            examId = routeObj.examId,
            state=state,
            moveToMCQ = {
                navController.navigate(Route.MCQRoute())

            },
            onAction = { event->

                viewModel.onQuiz(event)
            },
            viewModel = viewModel
        )
    }
}