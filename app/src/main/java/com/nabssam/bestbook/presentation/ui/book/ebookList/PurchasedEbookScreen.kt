package com.nabssam.bestbook.presentation.ui.book.ebookList

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.nabssam.bestbook.PDFViewActivity
import com.nabssam.bestbook.presentation.navigation.LoadingScreen
import com.nabssam.bestbook.presentation.ui.book.ebookList.components.PDFListItem
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import kotlinx.coroutines.flow.Flow
import java.io.File

@Composable
fun PurchasedEbookScreen(viewModel: ViewModelEbook, onEvent: (EventEbook) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingScreen()
        } else if (uiState.error != null) {
            ErrorScreen(modifier = Modifier.fillMaxSize(), message = uiState.error?: "an error occurred", {})
        } else {
            //  TODO: when no ebook is available, show a message
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.ebookList) { pdf ->
                    PDFListItem(
                        pdf = pdf,
                        onItemClick = {
                            if (!pdf.isDownloaded) {
                                onEvent(EventEbook.DownloadPdf(pdf))
                            } else {
                                val pdfFile = File(context.filesDir, "${pdf.name}.encrypted")
                                if (pdfFile.exists()) {
                                    openPDF(context, pdfFile)
                                } else {
                                    Toast.makeText(context, "PDF file not found", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

fun openPDF(context: Context, pdfFile: File) {
    // Use a PDF viewer library to open the file
    val intent = Intent(context, PDFViewActivity::class.java).apply {
        putExtra("pdf_path", pdfFile.absolutePath)
    }
    context.startActivity(intent)
}

fun <T> LiveData<T>.toFlow(): Flow<T> = this.asFlow()