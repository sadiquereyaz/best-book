package com.nabssam.bestbook.presentation.ui.pdf

/*
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PDFViewerScreen(pdfUrl: String) {
    val webView = rememberWebView()
    AndroidView(factory = {
        WebView(it).apply {
            settings.javaScriptEnabled = true
            loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdfUrl")
        }
    })
}
*/
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PDFViewerScreen(pdfUrl: String) {
    Log.d("PDFViewerScreen", "PDF URL: $pdfUrl")
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient() // Keeps navigation inside the WebView
            loadUrl("https://drive.usercontent.google.com/uc?id=1VchKgEW9GpS4-UPilJXQBWJ_RNiEqL_Q&export=download")
            loadUrl("https://drive.google.com/file/d/1VchKgEW9GpS4-UPilJXQBWJ_RNiEqL_Q/view")
//            loadUrl("https://www.drumstheword.com/pdf/Oasis_Supersonic.pdf")
//            loadUrl("$pdfUrl")
//            loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdfUrl")
        }
    })
}
