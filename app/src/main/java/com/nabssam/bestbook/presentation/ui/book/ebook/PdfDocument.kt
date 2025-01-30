package com.nabssam.bestbook.presentation.ui.book.ebook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nabssam.bestbook.presentation.ui.book.ebook.library.DownloadStatus
import com.nabssam.bestbook.presentation.ui.book.ebook.library.PdfDocument
import com.nabssam.bestbook.presentation.ui.book.ebook.library.PdfLibraryViewModel


// 4. UI Components
@Composable
fun PdfLibraryScreen(
    viewModel: PdfLibraryViewModel = hiltViewModel(),
    onPdfClick: (PdfDocument) -> Unit
) {
    val pdfs by viewModel.pdfs.collectAsState()
    val downloadedPdfs by viewModel.downloadedPdfs.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "PDF Library",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pdfs) { pdf ->
                PdfListItem(
                    pdf = pdf,
                    downloadStatus = viewModel.getDownloadStatus(pdf.id).collectAsState(initial = DownloadStatus.NotStarted).value,
                    onDownloadClick = { viewModel.downloadPdf(pdf) },
                    onPdfClick = { onPdfClick(pdf) }
                )
            }
        }
    }
}

@Composable
fun PdfListItem(
    pdf: PdfDocument,
    downloadStatus: DownloadStatus,
    onDownloadClick: () -> Unit,
    onPdfClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = downloadStatus is DownloadStatus.Completed,
                onClick = onPdfClick
            ),
        elevation = CardDefaults.cardElevation(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pdf.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                when (downloadStatus) {
                    is DownloadStatus.Downloading -> {
                        LinearProgressIndicator(
                            progress = downloadStatus.progress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                    is DownloadStatus.Error -> {
                        Text(
                            text = "Error: ${downloadStatus.message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    else -> {}
                }
            }

            when (downloadStatus) {
                is DownloadStatus.NotStarted -> {
                    IconButton(onClick = onDownloadClick) {
                        Icon(Icons.Default.Add, "Download PDF")
                    }
                }
                is DownloadStatus.Completed -> {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Downloaded",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                else -> {}
            }
        }
    }
}