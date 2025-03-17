package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.nabssam.bestbook.PDFViewActivity
import com.nabssam.bestbook.presentation.navigation.LoadingScreen
import com.nabssam.bestbook.presentation.ui.book.ebook.components.PDFListItem
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.utils.PDFDownloadWorker
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import kotlinx.coroutines.flow.Flow
import java.io.File

@Composable
fun PDFViewerScreen(uiState: UiStateEbook, onEvent: (EventEbook) -> Unit) {
    val context = LocalContext.current
    val workManager = remember { WorkManager.getInstance(context) }
    val pdfDownloadStatusHelper = remember { PDFDownloadStatusHelper(context) }

    // Load the initial download status for each PDF
    var pdfListState by remember {
        mutableStateOf(
            uiState.ebookList.map { pdf ->
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
            val pdfPath = workInfo.outputData.getString("pdf_path")
            Log.d("PDFViewerScreen", "Download succeeded for: $pdfName")

            if (pdfName != null) {
                // Update the download status in SharedPreferences
                pdfDownloadStatusHelper.setDownloadStatus(pdfName, pdfPath ?: "no path from worker")

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
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        if (uiState.isLoading)
            LoadingScreen()
        else if(uiState.error != null)
            ErrorScreen(modifier = Modifier.fillMaxSize(), message = uiState.error, {})
        else
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pdfListState) { pdf ->
                PDFListItem(
                    pdf = pdf,
                    onItemClick = {
                        if (!pdf.isDownloaded) {
                            // Start download
                            val workRequest =
                                pdf.url?.let { PDFDownloadWorker.createWorkRequest(pdf.name, it) }
                            if (workRequest != null) {
                                workManager.enqueueUniqueWork(
                                    "pdf_download",
                                    ExistingWorkPolicy.REPLACE,
                                    workRequest
                                )
                            }
                        } else {
                            // Open the downloaded PDF
                            val pdfFile = File(context.filesDir, "${pdf.name}.encrypted")
                            Log.d(
                                "PDFViewerScreen",
                                "Attempting to open PDF at: ${pdfFile.absolutePath}"
                            )
                            Log.d("PDFViewerScreen", "File exists: ${pdfFile.exists()}")

                            if (pdfFile.exists()) {
                                openPDF(context, pdfFile)
                            } else {
                                Toast.makeText(context, "PDF file not found", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                )
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