/*
package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDeepLinkRequest.Builder.Companion.fromUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun EbookPreviewScreen( modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            PDFView(context, null).apply {
                fromUri(uri)
                load()
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}





class PdfViewModel : ViewModel() {
    private val _pdfUri = MutableStateFlow<Uri?>(null)
    val pdfUri: StateFlow<Uri?> = _pdfUri

    fun loadPdf(context: Context, pdfUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 1. Download PDF
                val pdfBytes = downloadPdf(pdfUrl)

                // 2. Encrypt and save
                saveEncryptedPdf(context, pdfBytes)

                // 3. Decrypt to temp file
                val tempUri = decryptToTempFile(context)
                _pdfUri.value = tempUri
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun downloadPdf(url: String): ByteArray {
        // Use OkHttp/Retrofit
    }

    private fun saveEncryptedPdf(context: Context, bytes: ByteArray) {
        // Jetpack Security code from Step 2
    }

    private fun decryptToTempFile(context: Context): Uri {
        // Jetpack Security decryption + FileProvider
    }
}*/
