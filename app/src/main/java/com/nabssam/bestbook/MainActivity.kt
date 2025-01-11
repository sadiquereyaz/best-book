package com.nabssam.bestbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nabssam.bestbook.presentation.BestBookApp
import com.nabssam.bestbook.presentation.theme.BestBookTheme
import com.nabssam.bestbook.presentation.ui.components.OfflineDialog
import com.nabssam.bestbook.presentation.ui.quiz.ExamViewModel
import com.nabssam.bestbook.utils.EcommerceAppState
import com.nabssam.bestbook.utils.rememberEcommerceAppState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BestBookTheme {
                Surface(tonalElevation = 5.dp) {
//                    val appState: EcommerceAppState = rememberEcommerceAppState()
//                    if (!appState.isOnline) {
//                        OfflineDialog(onRetry = appState::refreshOnline)
//                    } else {
//                        BestBookApp(modifier = Modifier.fillMaxSize()) // Your app navigation
//
//                }
                        val examViewModel = hiltViewModel<ExamViewModel>()
                      examViewModel.fetch()
                        //CartScreen()
//                MCQScreen()
                        //b()
                        //QuizScreen()
//                PdfViewerScreenFromAssets()
//                PdfViewerScreen()
//                PdfViewerScreenFromUrlDirect()
                    }
                }
            }
        }
    }

