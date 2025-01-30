package com.nabssam.bestbook.presentation.ui.book.ebook

data class PDFItem(
    val name: String, // Display name of the PDF
    val url: String,  // Download link
    var isDownloaded: Boolean = false // Download status
)

val pdfList = listOf(
    PDFItem("Sample PDF 1", "https://icseindia.org/document/sample.pdf"),
    PDFItem("Sample PDF 2", "https://icseindia.org/document/sample.pdf"),
    PDFItem("Sample PDF 3", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"),
)