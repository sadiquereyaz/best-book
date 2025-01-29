package com.nabssam.bestbook.presentation.ui.book.ebook

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun SecurePdfScreen(
    viewModel: PdfViewModel = hiltViewModel(),
    pdfUrl: String
) {
    val pdfState by viewModel.pdfState.collectAsState()
    val context = LocalContext.current
    var currentPage by remember { mutableStateOf(0) }
    var totalPages by remember { mutableStateOf(0) }

    LaunchedEffect(pdfUrl) {
        viewModel.loadPdfFromUrl(pdfUrl)
    }

    when (val state = pdfState) {
        is PdfViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is PdfViewState.Success -> {
            PdfRendererView(
                pdfBytes = state.pdfBytes,
                currentPage = currentPage,
                onPageChange = { currentPage = it },
                onTotalPagesFound = { totalPages = it }
            )
        }
        is PdfViewState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.message}")
            }
        }
        PdfViewState.Initial -> {
            // Initial state UI
        }
    }
}

@Composable
fun PdfRendererView(
    pdfBytes: ByteArray,
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    onTotalPagesFound: (Int) -> Unit
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val scope = rememberCoroutineScope()

    // Create a temporary file for PdfRenderer
    val tempFile = remember {
        File.createTempFile("temp_pdf", "pdf", context.cacheDir).apply {
            deleteOnExit()
            writeBytes(pdfBytes)
        }
    }

    DisposableEffect(tempFile) {
        onDispose {
            bitmap?.recycle()
            tempFile.delete()
        }
    }

    LaunchedEffect(tempFile, currentPage) {
        scope.launch(Dispatchers.IO) {
            ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY).use { fd ->
                PdfRenderer(fd).use { renderer ->
                    onTotalPagesFound(renderer.pageCount)
                    renderer.openPage(currentPage.coerceIn(0, renderer.pageCount - 1)).use { page ->
                        val newBitmap = Bitmap.createBitmap(
                            page.width * 2,
                            page.height * 2,
                            Bitmap.Config.ARGB_8888
                        )
                        page.render(newBitmap, null, Matrix().apply { postScale(2f, 2f) }, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        bitmap?.recycle()
                        bitmap = newBitmap
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // PDF Page Display
        bitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "PDF page ${currentPage + 1}",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        // Navigation Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onPageChange((currentPage - 1).coerceAtLeast(0)) },
                enabled = currentPage > 0
            ) {
                Text("Previous")
            }

            Text("Page ${currentPage + 1}")

            Button(
                onClick = { onPageChange((currentPage + 1)) },
               // enabled = currentPage < (bitmap?.let { currentPage < totalPages - 1 } ?: false)
            ) {
                Text("Next")
            }
        }
    }
}
