package com.nabssam.bestbook.presentation.ui.pdf_bitmap

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PDFViewerScreenWebView(pdfUrl: String) {
    Log.d("PDFViewerScreen", "PDF URL: $pdfUrl")
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient() // Keeps navigation inside the WebView
            loadUrl("https://drive.usercontent.google.com/uc?id=1VchKgEW9GpS4-UPilJXQBWJ_RNiEqL_Q&export=download") // drive link downloadable
            loadUrl("https://drive.google.com/file/d/1VchKgEW9GpS4-UPilJXQBWJ_RNiEqL_Q/view")   // drive view only
//            loadUrl("https://www.drumstheword.com/pdf/Oasis_Supersonic.pdf")
//            loadUrl("$pdfUrl")
//            loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdfUrl")
        }
    })
}
