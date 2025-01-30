package com.nabssam.bestbook.presentation.ui.book.ebook.library

import android.content.Context
import com.nabssam.bestbook.data.repository.PdfEncryptionManager
import com.nabssam.bestbook.di.AppModule
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfLibraryRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val pdfEncryptionManager: PdfEncryptionManager,
    @ApplicationContext private val context: Context,
    @AppModule.ApplicationScope private val externalScope: CoroutineScope // Inject application scope
) {
    private val downloadStatusFlow = MutableStateFlow<Map<String, DownloadStatus>>(emptyMap())
    private val downloadedPdfsFlow = MutableStateFlow<List<PdfDocument>>(emptyList())

    init {
        // Load initially downloaded PDFs from local storage
        externalScope.launch(Dispatchers.IO) {
            loadDownloadedPdfs()
        }
    }

    private suspend fun loadDownloadedPdfs() {
        val pdfDir = File(context.filesDir, "pdfs")
        if (!pdfDir.exists()) return

        val downloadedFiles = pdfDir.listFiles()?.filter { it.extension == "enc" } ?: return
        val pdfs = downloadedFiles.mapNotNull { file ->
            val metadata = loadMetadata(file)
            metadata?.let {
                PdfDocument(
                    id = it.id,
                    title = it.title,
                    url = it.url,
                    fileSize = file.length(),
                    isDownloaded = true
                )
            }
        }
        downloadedPdfsFlow.value = pdfs
    }

    suspend fun downloadPdf(document: PdfDocument) {
        updateDownloadStatus(document.id, DownloadStatus.Downloading(0f))
        
        try {
            val request = Request.Builder().url(document.url).build()
            val response = okHttpClient.newCall(request).execute()
            
            if (!response.isSuccessful) {
                updateDownloadStatus(document.id, DownloadStatus.Error("Download failed"))
                return
            }

            response.body?.let { body ->
                val contentLength = body.contentLength()
                val inputStream = body.byteStream()
                val buffer = ByteArray(8192)
                var bytesRead: Int
                var totalBytesRead = 0L
                val bytes = ByteArrayOutputStream()

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    bytes.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    val progress = if (contentLength > 0) {
                        totalBytesRead.toFloat() / contentLength
                    } else 0f
                    updateDownloadStatus(document.id, DownloadStatus.Downloading(progress))
                }

                // Encrypt and save the file
                val encryptedFile = pdfEncryptionManager.encryptAndSave(
                    document.id,
                    bytes.toByteArray()
                )

                // Save metadata
                saveMetadata(document, encryptedFile)
                
                // Update status
                updateDownloadStatus(document.id, DownloadStatus.Completed)
                
                // Update downloaded PDFs list
                val updatedList = downloadedPdfsFlow.value.toMutableList()
                updatedList.add(document.copy(isDownloaded = true))
                downloadedPdfsFlow.value = updatedList
            }
        } catch (e: Exception) {
            updateDownloadStatus(document.id, DownloadStatus.Error(e.message ?: "Unknown error"))
        }
    }

    private fun updateDownloadStatus(documentId: String, status: DownloadStatus) {
        val currentStatuses = downloadStatusFlow.value.toMutableMap()
        currentStatuses[documentId] = status
        downloadStatusFlow.value = currentStatuses
    }

    fun getDownloadStatus(documentId: String): Flow<DownloadStatus> = downloadStatusFlow
        .map { it[documentId] ?: DownloadStatus.NotStarted }

    fun getDownloadedPdfs(): Flow<List<PdfDocument>> = downloadedPdfsFlow

    private fun saveMetadata(document: PdfDocument, file: File) {
        val metadata = JSONObject().apply {
            put("id", document.id)
            put("title", document.title)
            put("url", document.url)
            put("downloadDate", System.currentTimeMillis())
        }
        
        File(file.parentFile, "${document.id}.meta").writeText(metadata.toString())
    }

    private fun loadMetadata(file: File): PdfDocument? {
        val metaFile = File(file.parentFile, "${file.nameWithoutExtension}.meta")
        if (!metaFile.exists()) return null

        return try {
            val json = JSONObject(metaFile.readText())
            PdfDocument(
                id = json.getString("id"),
                title = json.getString("title"),
                url = json.getString("url"),
                fileSize = file.length(),
                isDownloaded = true
            )
        } catch (e: Exception) {
            null
        }
    }
}