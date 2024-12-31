package com.imageclasses.imageclasses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.presentation.home.ViewModelHomeScreen
import com.imageclasses.imageclasses.presentation.addnote.AddNoteScreen
import com.imageclasses.imageclasses.presentation.addnote.AddNotesScreen
import com.imageclasses.imageclasses.presentation.addnote.ViewModelAddNotesScreen
import com.imageclasses.imageclasses.presentation.home.HomeScreen
import com.imageclasses.imageclasses.ui.theme.ImageClassesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageClassesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController: NavHostController = rememberNavController()
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = AddNotesScreen
                    ) {
                        composable<HomeScreen> {
//                            val viewModel = viewModel<ViewModelHomeScreen>()
//                            val state by viewModel.stateNote.collectAsState()
                            HomeScreen(
                                navController = navController
                            )
                        }
                        composable<AddNotesScreen> {
                            val viewModel = viewModel<ViewModelAddNotesScreen>()
                            val state by viewModel.stateNote.collectAsState()
                            AddNoteScreen(
                                navController = navController,
                                state = state,
                                event = {
                                    viewModel.onEvent(it)
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}