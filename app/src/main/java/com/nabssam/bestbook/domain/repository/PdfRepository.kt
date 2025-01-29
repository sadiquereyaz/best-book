package com.nabssam.bestbook.domain.repository

import java.io.File

interface PdfRepository {
    suspend fun downloadAndStorePdf(url: String): Result<File>
    suspend fun loadPdf(file: File): Result<ByteArray>
}