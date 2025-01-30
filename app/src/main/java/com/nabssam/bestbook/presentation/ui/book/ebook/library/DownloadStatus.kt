package com.nabssam.bestbook.presentation.ui.book.ebook.library
sealed class DownloadStatus {
    object NotStarted : DownloadStatus()
    data class Downloading(val progress: Float) : DownloadStatus()
    object Completed : DownloadStatus()
    data class Error(val message: String) : DownloadStatus()
}
data class PdfDocument(
    val id: String,
    val title: String,
    val url: String,
    val fileSize: Long = 0,
    val isDownloaded: Boolean = false
)
