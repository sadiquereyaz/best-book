package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import android.content.SharedPreferences
import java.io.File

class PDFDownloadStatusHelper(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PDF_DOWNLOAD_STATUS", Context.MODE_PRIVATE)

    // Save the download status and file path of a PDF
    fun setDownloadStatus(pdfName: String, filePath: String) {
        sharedPreferences.edit()
            .putBoolean(pdfName, true)
            .putString("${pdfName}_path", filePath)
            .apply()
    }

    // Get the file path of a downloaded PDF
    fun getFilePath(pdfName: String): String? {
        return sharedPreferences.getString("${pdfName}_path", null)
    }
    // Get the download status of a PDF
    fun getDownloadStatus(pdfName: String): Boolean {
        return sharedPreferences.getBoolean(pdfName, false)
    }

    // Clear all statuses and remove all files
    fun deleteAllDownloadedPDFs() {
        val keys = sharedPreferences.all.keys
        for (key in keys) {
            if (key.endsWith("_path")) {
                val filePath = sharedPreferences.getString(key, null)
                filePath?.let {
                    val file = File(it)
                    if (file.exists()) file.delete()
                }
            }
        }
        clearAllStatuses()
    }

    // Clear all statuses
    private fun clearAllStatuses() {
        sharedPreferences.edit().clear().apply()
    }
}
