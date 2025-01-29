package com.nabssam.bestbook.presentation.ui.book.ebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.repository.PdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val pdfRepository: PdfRepository
) : ViewModel() {
    private val _pdfState = MutableStateFlow<PdfViewState>(PdfViewState.Initial)
    val pdfState: StateFlow<PdfViewState> = _pdfState.asStateFlow()

    private var currentPdfFile: File? = null

    fun loadPdfFromUrl(url: String) {
        viewModelScope.launch {
            _pdfState.value = PdfViewState.Loading

            pdfRepository.downloadAndStorePdf(url)
                .onSuccess { file ->
                    currentPdfFile = file
                    loadStoredPdf(file)
                }
                .onFailure { error ->
                    _pdfState.value = PdfViewState.Error(error.message ?: "Unknown error")
                }
        }
    }

    private fun loadStoredPdf(file: File) {
        viewModelScope.launch {
            pdfRepository.loadPdf(file)
                .onSuccess { bytes ->
                    _pdfState.value = PdfViewState.Success(bytes)
                }
                .onFailure { error ->
                    _pdfState.value = PdfViewState.Error(error.message ?: "Failed to load PDF")
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up temporary files if needed
    }
}