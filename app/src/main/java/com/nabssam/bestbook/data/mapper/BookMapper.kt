package com.nabssam.bestbook.data.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.book.ebook.Ebook
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Instant


class BookMapper(
    private val pdfDownloadStatusHelper: PDFDownloadStatusHelper
) {
    fun dtoToDomain(dto: BookDto): Book {
        Log.d("DTO_TO_DOMAIN", dto.toString())
        return Book(
            id = dto._id,
            name = dto.name,
            price = dto.price ?: 0,
            description = dto.description,
            imageUrls = dto.images ?: emptyList(),
            exam = dto.targetExam,
            coverUrl = dto.coverImage,
            author = dto.author,
            hardCopyDis = dto.hardcopyDiscount ?: 0,
            publisher = dto.publisher,
            reviewCount = dto.reviewStats?.reviewCount,
            averageRate = dto.reviewStats?.averageRating,
            stock = dto.stock ?: 0,
            isbn = dto.isbn,
            language = dto.language,
            publishDate = dto.publicationDate?.parseFromIso(),
            ebookDis = dto.ebookDiscount,
            noOfPages = dto.pages,
            ebookUrl = dto.eBook
        )
    }

    fun ebookDtoToDomain(dto: BookDto): Ebook {
        return Ebook(
            id = dto._id,
            name = dto.name,
            exam = dto.targetExam,
            coverUrl = dto.coverImage,
            author = dto.description,
            url = dto.eBook,
            isDownloaded = pdfDownloadStatusHelper.getDownloadStatus(dto.name)
        )
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
fun String.parseFromIso(): String {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return this
    else {// Parse the ISO date-time string
        try {
            val instant = Instant.parse(this)

            // Convert to LocalDate (ignoring time)
            val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()

            // Format as needed (e.g., "15 Jan 2025")
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            return localDate.format(formatter)
        } catch (e: Exception) {
            return this
        }
    }
}
