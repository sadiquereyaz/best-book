package com.nabssam.bestbook.presentation.ui.book.ebook

data class Ebook(
    val id: String,
    val exam: String,
    val name: String, // Display name of the PDF
    val author: String, // Display name of the PDF
    val url: String,  // Download link
    val coverUrl: String = "http://res.cloudinary.com/dniu1zxdq/image/upload/v1737962343/tdiudk2hhebt4faznxhq.jpg\"",
    var isDownloaded: Boolean = false // Download status
)
/*

val pdfList = listOf(
    Ebook("Sample PDF 1", "https://icseindia.org/document/sample.pdf"),
    Ebook("Sample PDF 2", "https://icseindia.org/document/sample.pdf"),
    Ebook("Sample PDF 3", "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"),
)*/
