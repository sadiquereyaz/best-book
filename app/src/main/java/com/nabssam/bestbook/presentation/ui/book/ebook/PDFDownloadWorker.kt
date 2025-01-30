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

        // Temporary file to store the downloaded PDF
//        val tempFile = File(applicationContext.cacheDir, "$pdfName.temp.pdf")
        val tempFile = File(applicationContext.cacheDir, "temp_${System.currentTimeMillis()}.pdf")
        // Final encrypted file
        val encryptedFile = File(applicationContext.filesDir, "$pdfName.encrypted")
//        val encryptedFile = File(applicationContext.filesDir, "$pdfName.encrypted.pdf")

        try {
            // Download the PDF
            val request = Request.Builder().url(pdfUrl).build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.byteStream()?.use { inputStream ->
                    tempFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                // Encrypt the downloaded PDF
                PDFEncryptionHelper.encryptPDF(context = applicationContext, inputFile = tempFile, outputFile = encryptedFile)

                // Delete the temporary file
                tempFile.delete()

                // Log the file path after encryption
                Log.d("PDFDownloadWorker", "Encrypted PDF saved at: ${encryptedFile.absolutePath}")
                Log.d("PDFDownloadWorker", "File size: ${encryptedFile.length()} bytes")

                // Pass the pdfName in the output data
                val outputData = workDataOf("pdf_name" to pdfName)
                Result.success(outputData)
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("PDFDownloadWorker", "Download or encryption failed: ${e.message}")
            Result.failure()
        } finally {
            // Ensure the temporary file is deleted in case of any failure
            if (tempFile.exists()) {
                tempFile.delete()
            }
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