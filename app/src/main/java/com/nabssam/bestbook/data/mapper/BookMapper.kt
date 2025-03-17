package com.nabssam.bestbook.data.mapper

import android.util.Log
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.book.ebook.Ebook
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper

class BookMapper (
    private val pdfDownloadStatusHelper: PDFDownloadStatusHelper
){
    fun dtoToDomain(dto: BookDto): Book {
//        Log.d("DTO_TO_DOMAIN", dto.toString())
        return Book(
            id = dto._id,
            name = dto.name,
            price = dto.price ?: 0,
            description = dto.description,
            imageUrls = dto.images,
            exam = dto.targetExam,
            coverUrl = dto.coverImage,
//            author = dto.author,
            hardCopyDis = dto.hardcopyDiscount ?: 0,
            publisher = dto.publisher,
            //rate = dto.rating,
            averageRate = dto.reviewStats?.averageRating?.toDouble() ?: 0.1,
            stock = dto.stock ?: 0,
            //isbn = dto.isbn,
            //language = dto.language,
            // publishDate = dto.publicationDate,
            ebookDis = dto.ebookDiscount,
            //noOfPages = dto.pages,
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