package com.nabssam.bestbook.presentation.ui.book.ebook

data class Ebook(
    val id: String,
    val exam: String? = null,
    val name: String, // Display name of the PDF
    val author: String? = null, // Display name of the PDF
    val url: String? = null,  // Download link
    val coverUrl: String? = null,
    var isDownloaded: Boolean = false // Download status
)
/*

val pdfList = listOf(
    Ebook("Sample PDF 1", "https://icseindia.org/document/sample.pdf"),
    Ebook("Sample PDF 2", "https://icseindia.org/document/sample.pdf"),
    Ebook("Sample PDF 3", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"),
)*/
