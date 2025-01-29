package com.nabssam.bestbook.data.repository

import android.content.Context
import com.nabssam.bestbook.domain.repository.PdfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PdfRepositoryImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val encryptionManager: PdfEncryptionManager,
    private val context: Context
) : PdfRepository {

    override suspend fun downloadAndStorePdf(url: String): Result<File> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()
            
            if (!response.isSuccessful) {
                return@withContext Result.failure(IOException("Failed to download PDF"))
            }

            val pdfBytes = response.body?.bytes() ?: throw IOException("Empty response")
            val encryptedFile = encryptionManager.encryptAndSave(
                fileName = url.substringAfterLast("/"),
                pdfData = pdfBytes
            )
            Result.success(encryptedFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadPdf(file: File): Result<ByteArray> = withContext(Dispatchers.IO) {
        try {
            val decryptedBytes = encryptionManager.readEncryptedFile(file)
            Result.success(decryptedBytes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}