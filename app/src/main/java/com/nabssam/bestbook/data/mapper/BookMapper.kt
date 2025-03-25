package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.book.ebookList.Ebook
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import com.nabssam.bestbook.utils.parseDateFromIso


class BookMapper(
    private val pdfDownloadStatusHelper: PDFDownloadStatusHelper
) {
    fun dtoToDomain(dto: BookDto): Book {
//        Log.d("DTO_TO_DOMAIN", dto.toString())
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
            reviewCount = dto.bookListReviewStats?.reviewCount,
            averageRate = dto.bookListReviewStats?.averageRating ?: 3.1,
            stock = dto.stock ?: 0,
            isbn = dto.isbn,
            language = dto.language,
            publishDate = dto.publicationDate?.parseDateFromIso(),
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