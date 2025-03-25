package com.nabssam.bestbook.presentation.ui.pdf_bitmap

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nabssam.bestbook.utils.PdfToBitmapConvertor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PdfViewerScreenFromAssets(
    modifier: Modifier = Modifier,
    pdfFileName: String = "CN_MOHD_HAMMAD_QADIR.pdf" // PDF file name in assets
) {
    val context = LocalContext.current
    val pdfBitmapConverter = remember {
        PdfToBitmapConvertor(context)
    }
    var renderedPages by remember {
        mutableStateOf<List<Bitmap>>(emptyList())
    }

    val scope = rememberCoroutineScope()

    // Load PDF from assets and convert to bitmaps
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val assetManager = context.assets
            val inputStream = assetManager.open(pdfFileName)
            renderedPages = pdfBitmapConverter.pdfToBitmaps(inputStream)
        }
    }

    // Render the pages
    if (renderedPages.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading PDF...")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(renderedPages) { index, page ->
                BitmapImageViewer(page = page)
            }
        }
    }
}