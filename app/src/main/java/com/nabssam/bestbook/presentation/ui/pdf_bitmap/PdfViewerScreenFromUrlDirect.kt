package com.nabssam.bestbook.presentation.ui.pdf_bitmap

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import com.nabssam.bestbook.utils.PdfToBitmapConvertor
import kotlinx.coroutines.launch

@Composable
fun PdfViewerScreenFromUrlDirect(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pdfBitmapConverter = remember { PdfToBitmapConvertor(context) }
    var pdfUrl by remember { mutableStateOf("https://www.drumstheword.com/pdf/Oasis_Supersonic.pdf") }
    var renderedPages by remember { mutableStateOf<List<Bitmap>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var show by remember { mutableStateOf(true) }
    Column(
        modifier = modifier.fillMaxSize().padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (show) {
            OutlinedTextField(
                value = pdfUrl,
                onValueChange = { pdfUrl = it },
                label = { Text("Enter PDF URL") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (pdfUrl.isNotEmpty()) {
                        IconButton(onClick = { pdfUrl = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )


            GradientButton(
                onClick = {
                    show = false
                    scope.launch {
                        isLoading = true
                        try {
                            renderedPages = fetchAndRenderPdf(pdfUrl, pdfBitmapConverter)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Render PDF")
            }
        }
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading PDF...")
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(renderedPages) { index, page ->
                    BitmapImageViewer(page = page)
                }
            }
        }
    }
}