package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.nabssam.bestbook.PDFViewActivity
import kotlinx.coroutines.flow.Flow
import java.io.File

@Composable
fun PDFViewerScreen() {
    val context = LocalContext.current
    val workManager = remember { WorkManager.getInstance(context) }
    val pdfDownloadStatusHelper = remember { PDFDownloadStatusHelper(context) }

    // Load the initial download status for each PDF
    var pdfListState by remember {
        mutableStateOf(
            pdfList.map { pdf ->
                pdf.copy(isDownloaded = pdfDownloadStatusHelper.getDownloadStatus(pdf.name))
            }
        )
    }

    // Observe WorkManager state
    val workInfoList by workManager.getWorkInfosForUniqueWorkLiveData("pdf_download")
        .toFlow()
        .collectAsState(initial = emptyList())

    // Handle WorkManager state changes
    LaunchedEffect(workInfoList) {
        val workInfo = workInfoList.firstOrNull()
        Log.d("PDFViewerScreen", "WorkInfo state: ${workInfo?.state}")

        if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
            val pdfName = workInfo.outputData.getString("pdf_name")
            Log.d("PDFViewerScreen", "Download succeeded for: $pdfName")

            if (pdfName != null) {
                // Update the download status in SharedPreferences
                pdfDownloadStatusHelper.setDownloadStatus(pdfName, true)

                // Update the UI state
                pdfListState = pdfListState.map { pdf ->
                    if (pdf.name == pdfName) pdf.copy(isDownloaded = true) else pdf
                }
            } else {
                Log.e("PDFViewerScreen", "pdfName is null in WorkInfo output data")
            }
        }
    }

    // Display the list of PDFs
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(pdfListState) { pdf ->
            PDFListItem(
                pdf = pdf,
                onItemClick = {
                    if (!pdf.isDownloaded) {
                        // Start download
                        val workRequest = PDFDownloadWorker.createWorkRequest(pdf.name, pdf.url)
                        workManager.enqueueUniqueWork(
                            "pdf_download",
                            ExistingWorkPolicy.REPLACE,
                            workRequest
                        )
                    } else {
                        // Open the downloaded PDF
                        val pdfFile = File(context.filesDir, "${pdf.name}.encrypted.pdf")
                        Log.d(
                            "PDFViewerScreen",
                            "Attempting to open PDF at: ${pdfFile.absolutePath}"
                        )
                        Log.d("PDFViewerScreen", "File exists: ${pdfFile.exists()}")

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

fun openPDF(context: Context, pdfFile: File) {
    // Use a PDF viewer library to open the file
    val intent = Intent(context, PDFViewActivity::class.java).apply {
        putExtra("pdf_path", pdfFile.absolutePath)
    }
    context.startActivity(intent)
}

@Composable
fun PDFListItem(pdf: PDFItem, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = pdf.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (pdf.isDownloaded) {
                Text(
                    text = "Downloaded - Click to Open",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green
                )
            } else {
                Text(
                    text = "Click to Download",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue
                )
            }
        }
    }
}


fun <T> LiveData<T>.toFlow(): Flow<T> = this.asFlow()


