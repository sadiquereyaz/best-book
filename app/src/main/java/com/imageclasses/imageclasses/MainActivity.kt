package com.imageclasses.imageclasses

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.imageclasses.imageclasses.ui.feature.bookList.PdfViewerScreen

import com.imageclasses.imageclasses.ui.feature.bookList.PdfViewerScreenFromAssets
import com.imageclasses.imageclasses.ui.feature.bookList.PdfViewerScreenFromUrlDirect

import com.imageclasses.imageclasses.ui.theme.ImageClassesTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageClassesTheme {
//                AppNavigation(modifier = Modifier.fillMaxSize()) // Your app navigation
//                PdfViewerScreenFromAssets()
//                PdfViewerScreen()
                PdfViewerScreenFromUrlDirect()
            }
        }
    }
}

