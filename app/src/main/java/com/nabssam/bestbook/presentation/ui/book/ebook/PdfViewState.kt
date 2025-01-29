package com.nabssam.bestbook.presentation.ui.book.ebook

sealed class PdfViewState {
    object Initial : PdfViewState()
    object Loading : PdfViewState()
    data class Success(val pdfBytes: ByteArray) : PdfViewState()
    data class Error(val message: String) : PdfViewState()
}