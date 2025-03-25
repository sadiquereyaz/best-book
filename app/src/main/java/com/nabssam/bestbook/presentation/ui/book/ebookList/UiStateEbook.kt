package com.nabssam.bestbook.presentation.ui.book.ebookList

data class UiStateEbook(
    val error: String? = null,
    val isLoading: Boolean = false,
    val ebookList: List<Ebook> = emptyList()
)

sealed class EventEbook {
    object FetchEbook : EventEbook()
    object Retry : EventEbook()
    data class DownloadPdf(val pdf: Ebook) : EventEbook()
}
sealed class DownloadState {
    object Idle : DownloadState()
    object Downloading : DownloadState()
    data class Success(val pdfName: String) : DownloadState()
    data class Error(val message: String) : DownloadState()
}