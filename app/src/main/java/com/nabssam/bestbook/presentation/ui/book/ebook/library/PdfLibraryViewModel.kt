package com.nabssam.bestbook.presentation.ui.book.ebook.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdfLibraryViewModel @Inject constructor(
    private val repository: PdfLibraryRepository
) : ViewModel() {

    private val _pdfs = MutableStateFlow(listOf<PdfDocument>())
    val pdfs = _pdfs.asStateFlow()

    init {
        // Load your initial PDF list here
        viewModelScope.launch {
            _pdfs.value = listOf(
                PdfDocument("1", "Sample PDF 1", "https://example.com/pdf1.pdf"),
                PdfDocument("2", "Sample PDF 2", "https://example.com/pdf2.pdf")
                // Add more PDFs
            )
        }
    }

    fun downloadPdf(document: PdfDocument) {
        viewModelScope.launch {
            repository.downloadPdf(document)
        }
    }

    fun getDownloadStatus(documentId: String): Flow<DownloadStatus> =
        repository.getDownloadStatus(documentId)

    val downloadedPdfs = repository.getDownloadedPdfs()
}
