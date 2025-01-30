package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

// Download PDF Using WorkManager

class PDFDownloadWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val pdfUrl = inputData.getString("pdf_url") ?: return@withContext Result.failure()
        val pdfName = inputData.getString("pdf_name") ?: return@withContext Result.failure()
        val pdfFile = File(applicationContext.filesDir, "$pdfName.pdf")

        try {
            val request = Request.Builder().url(pdfUrl).build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.use { inputStream ->
                    pdfFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                // Log the file path after saving
                Log.d("PDFDownloadWorker", "PDF saved at: ${pdfFile.absolutePath}")
                Log.d("PDFDownloadWorker", "File size: ${pdfFile.length()} bytes")

                // Pass the pdfName in the output data
                val outputData = workDataOf("pdf_name" to pdfName)
                Result.success(outputData)
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("PDFDownloadWorker", "Download failed: ${e.message}")
            Result.failure()
        }
    }

    companion object {
        fun createWorkRequest(pdfName: String, pdfUrl: String): OneTimeWorkRequest {
            val inputData = Data.Builder()
                .putString("pdf_name", pdfName)
                .putString("pdf_url", pdfUrl)
                .build()
            return OneTimeWorkRequest.Builder(PDFDownloadWorker::class.java)
                .setInputData(inputData)
                .build()
        }
    }
}