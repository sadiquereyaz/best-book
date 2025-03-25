package com.nabssam.bestbook.presentation.ui.book.ebookList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.nabssam.bestbook.domain.repository.EbookRepository
import com.nabssam.bestbook.utils.pdf.PDFDownloadWorker
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelEbook @Inject constructor(
    private val repository: EbookRepository,
    private val workManager: WorkManager,
    private val pdfDownloadStatusHelper: PDFDownloadStatusHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateEbook())
    val uiState = _uiState.asStateFlow()

    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState = _downloadState.asStateFlow()

    init {
        onEvent(EventEbook.FetchEbook)
        observeDownloadStatus()
    }

    fun onEvent(event: EventEbook) {
        when (event) {
            is EventEbook.FetchEbook -> fetchEbook()
            is EventEbook.Retry -> fetchEbook()
            is EventEbook.DownloadPdf -> downloadPdf(event.pdf)
        }
    }

    private fun fetchEbook() {
        viewModelScope.launch {
            repository.fetchEbook().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            ebookList = resource.data?.map { pdf ->
                                pdf.copy(isDownloaded = pdfDownloadStatusHelper.getDownloadStatus(pdf.name))
                            } ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun downloadPdf(pdf: Ebook) {
        val workRequest = pdf.url?.let { PDFDownloadWorker.createWorkRequest(pdf.name, it) }
        if (workRequest != null) {
            workManager.enqueueUniqueWork(
                //uniqueWorkName = "pdf_download_${pdf.name}",
                uniqueWorkName = "pdf_download",
                existingWorkPolicy = ExistingWorkPolicy.REPLACE,
                request = workRequest
            )
        }
    }

    private fun observeDownloadStatus() {
        viewModelScope.launch {
            workManager.getWorkInfosForUniqueWorkLiveData("pdf_download")
                .toFlow()
                .collect { workInfoList ->
                    val workInfo = workInfoList.firstOrNull()
                    if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                        val pdfName = workInfo.outputData.getString("pdf_name")
                        val pdfPath = workInfo.outputData.getString("pdf_path")
                        if (pdfName != null) {
                            pdfDownloadStatusHelper.setDownloadStatus(pdfName, pdfPath ?: "no path from worker")
                            _uiState.value = _uiState.value.copy(
                                ebookList = _uiState.value.ebookList.map { pdf ->
                                    if (pdf.name == pdfName) pdf.copy(isDownloaded = true) else pdf
                                }
                            )
                        }
                    }
                }
        }
    }
}