package com.nabssam.bestbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.ui.AppNavigation
import com.nabssam.bestbook.ui.theme.ImageClassesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageClassesTheme {
                Surface(tonalElevation = 5.dp) {
                    AppNavigation(modifier = Modifier.fillMaxSize()) // Your app navigation
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

