package com.imageclasses.imageclasses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.imageclasses.imageclasses.ui.AppNavigation
import com.imageclasses.imageclasses.ui.theme.ImageClassesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageClassesTheme {
                AppNavigation(modifier = Modifier.fillMaxSize()) // Your app navigation
                //b()
//                PdfViewerScreenFromAssets()
//                PdfViewerScreen()
//                PdfViewerScreenFromUrlDirect()
            }
        }
    }
}

