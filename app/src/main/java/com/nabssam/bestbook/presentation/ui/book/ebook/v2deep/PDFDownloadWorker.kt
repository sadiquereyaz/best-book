package com.nabssam.bestbook.presentation.ui.book.ebook.v2deep

import android.content.Context
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class PDFDownloadWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val pdfUrl = inputData.getString("pdf_url") ?: return@withContext Result.failure()
        val pdfFile = File(applicationContext.filesDir, "encrypted_pdf.pdf")

        try {
            val request = Request.Builder().url(pdfUrl).build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.use { inputStream ->
                    pdfFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        fun createWorkRequest(pdfUrl: String): OneTimeWorkRequest {
            val inputData = Data.Builder().putString("pdf_url", pdfUrl).build()
            return OneTimeWorkRequest.Builder(PDFDownloadWorker::class.java)
                .setInputData(inputData)
                .build()
        }
    }
}