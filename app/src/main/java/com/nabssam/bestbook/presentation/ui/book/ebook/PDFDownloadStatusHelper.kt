package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import android.content.SharedPreferences

class PDFDownloadStatusHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PDF_DOWNLOAD_STATUS", Context.MODE_PRIVATE)

    // Save the download status of a PDF
    fun setDownloadStatus(pdfName: String, isDownloaded: Boolean) {
        sharedPreferences.edit().putBoolean(pdfName, isDownloaded).apply()
    }

    // Get the download status of a PDF
    fun getDownloadStatus(pdfName: String): Boolean {
        return sharedPreferences.getBoolean(pdfName, false)
    }

    // Clear all download statuses (optional)
    fun clearAllStatuses() {
        sharedPreferences.edit().clear().apply()
    }
}