package com.nabssam.bestbook.utils.pdf

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.nabssam.bestbook.presentation.ui.book.ebookList.PDFEncryptionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

// Download PDF Using WorkManager
class PDFDownloadWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val pdfUrl = inputData.getString("pdf_url") ?: return@withContext Result.failure()
        val pdfName = inputData.getString("pdf_name") ?: return@withContext Result.failure()

        // Temporary files for different stages
        val tempFile = File(applicationContext.cacheDir, "temp_${System.currentTimeMillis()}.pdf")
        val watermarkedFile = File(applicationContext.cacheDir, "watermarked_${System.currentTimeMillis()}.pdf")
        // Final encrypted file
        val encryptedFile = File(applicationContext.filesDir, "$pdfName.encrypted")

        try {
            // Download the PDF
            /**
             * 1. val request = Request.Builder().url(pdfUrl).build()
             *
             * Request.Builder(): This creates a builder object for constructing an HTTP request.
             * .url(pdfUrl): This sets the URL of the PDF file to be downloaded. pdfUrl is assumed to be a string variable containing the URL.
             * .build(): This builds the Request object based on the specified URL.
             * 2. val response = OkHttpClient().newCall(request).execute()
             *
             * OkHttpClient(): This creates an instance of the OkHttp client, a popular and efficient HTTP client library for Android and Java.
             * .newCall(request): This creates a new call object from the built Request.
             * .execute(): This executes the HTTP request synchronously, meaning the current thread will block until the response is received. The response variable will hold the HTTP response.
             * 3. if (response.isSuccessful)
             *
             * This checks if the HTTP response indicates a successful request (e.g., status code 200 OK).
             *
             *
             * 4. response.body?.byteStream()?.use { inputStream -> ... }
             *
             * response.body: This retrieves the response body, which contains the data of the downloaded PDF.
             *
             * ?.byteStream(): This gets an InputStream from the response body, allowing you to read the PDF data as a stream of bytes.
             * The ?. is the safe call operator, which prevents a NullPointerException if response.body is null.
             *
             * .use { inputStream -> ... }: This is a Kotlin use function, which ensures that the InputStream is closed automatically after the block of code is executed,
             * even if exceptions occur. This is crucial for resource management.
             *
             *
             * 5. tempFile.outputStream().use { outputStream -> ... }
             *
             * tempFile.outputStream(): This creates an OutputStream from the tempFile object, allowing you to write data to the temporary file.
             * tempFile is assumed to be a File object representing the temporary file where the PDF will be saved.
             *
             * .use { outputStream -> ... }: Similar to the previous use block, this ensures that the OutputStream is closed automatically.
             *
             *
             * 6. inputStream.copyTo(outputStream)
             * This copies the bytes from the inputStream (the downloaded PDF data) to the outputStream (the temporary file).
             * This effectively saves the PDF data to the file
             */
            val request = Request.Builder().url(pdfUrl).build()
            val response = OkHttpClient().newCall(request).execute()

            if (response.isSuccessful) {
                response.body?.byteStream()?.use { inputStream ->
                    tempFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                // Add watermark to the downloaded PDF
                PDFWatermarkHelper.addWatermark(
                    context = applicationContext,
                    inputFile = tempFile,
                    outputFile = watermarkedFile,
                    watermarkText = "+919506198939",
                    fontSize = 40f
                )

                // Encrypt the watermarked PDF
                PDFEncryptionHelper.encryptPDF(
                    context = applicationContext,
                    inputFile = watermarkedFile,
                    outputFile = encryptedFile
                )

                // Clean up temporary files
                tempFile.delete()
                watermarkedFile.delete()

                // Log details
                Log.d("PDFDownloadWorker", "Encrypted and watermarked PDF saved at: ${encryptedFile.absolutePath}")
                Log.d("PDFDownloadWorker", "File size: ${encryptedFile.length()} bytes")

                // Pass the pdfName in the output data
                val outputData = workDataOf(
                    "pdf_name" to pdfName,
                    "pdf_path" to encryptedFile.absolutePath
                )
                Result.success(outputData)
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("PDFDownloadWorker", "Download, watermark, or encryption failed: ${e.message}")
            Result.failure()
        } finally {
            // Ensure temporary files are deleted
            if (tempFile.exists()) tempFile.delete()
            if (watermarkedFile.exists()) watermarkedFile.delete()
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