package com.nabssam.bestbook.presentation.ui.book.ebook

sealed class PdfViewState {
    data object Initial : PdfViewState()
    data object Loading : PdfViewState()
    data class Success(val pdfBytes: ByteArray) : PdfViewState()
    data class Error(val message: String) : PdfViewState()
}