package com.nabssam.bestbook.presentation.ui


import android.app.Activity
import android.app.appsearch.SearchResults
import android.graphics.Bitmap
import android.net.Uri
import android.view.WindowManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.utils.PdfToBitmapConvertor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


@Composable
fun PdfViewerScreen(
    modifier: Modifier = Modifier
) {
//    thia is for uploading pdf from device
    val context = LocalContext.current
    val pdfBitmapConverter = remember {
        PdfToBitmapConvertor(context)
    }
    var pdfUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var renderedPages by remember {
        mutableStateOf<List<Bitmap>>(emptyList())
    }
    var searchText by remember {
        mutableStateOf("")
    }
    var searchResults by remember {
        mutableStateOf(emptyList<SearchResults>())
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pdfUri) {
        pdfUri?.let { uri ->
            renderedPages = pdfBitmapConverter.pdfToBitmaps(uri)
        }
    }

    val choosePdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        pdfUri = it
    }

    if(pdfUri == null) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                choosePdfLauncher.launch("application/pdf")
            }) {
                Text(text = "Choose PDF")
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                itemsIndexed(renderedPages) { index, page ->
                    PdfPage(
                        page = page,

                    )
                }
            }
            Button(onClick = {
                choosePdfLauncher.launch("application/pdf")
            }) {
                Text(text = "Choose another PDF")
            }

        }
    }
}


@Composable
fun PdfViewerScreenFromAssets(
    modifier: Modifier = Modifier,
    pdfFileName: String = "CN_MOHD_HAMMAD_QADIR.pdf" // PDF file name in assets
) {
    val context = LocalContext.current
    val pdfBitmapConverter = remember {
        PdfToBitmapConvertor(context)
    }
    var renderedPages by remember {
        mutableStateOf<List<Bitmap>>(emptyList())
    }

    val scope = rememberCoroutineScope()

    // Load PDF from assets and convert to bitmaps
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val assetManager = context.assets
            val inputStream = assetManager.open(pdfFileName)
            renderedPages = pdfBitmapConverter.pdfToBitmaps(inputStream)
        }
    }

    // Render the pages
    if (renderedPages.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading PDF...")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(renderedPages) { index, page ->
                PdfPage(page = page)
            }
        }
    }
}

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
       if(show) {
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


           Button(
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
                    PdfPage(page = page)
                }
            }
        }
    }
}


suspend fun fetchAndRenderPdf(url: String, converter: PdfToBitmapConvertor): List<Bitmap> {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    return withContext(Dispatchers.IO) {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Failed to fetch PDF: ${response.message}")
            response.body?.byteStream()?.let { inputStream ->
                converter.pdfToBitmaps(inputStream)
            } ?: emptyList()
        }
    }
}


@Composable
fun PdfPage(
    page: Bitmap,
    modifier: Modifier = Modifier,

) {
    AsyncImage(
        model = page,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(page.width.toFloat() / page.height.toFloat())
            .drawWithContent {
                drawContent()


            }
    )
}


@Composable
fun RestrictScreenshot(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window
    val view = LocalView.current

    DisposableEffect(Unit) {
        // Enable screenshot restriction
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        onDispose {
            // Remove screenshot restriction when the composable is disposed
            window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    content()
}