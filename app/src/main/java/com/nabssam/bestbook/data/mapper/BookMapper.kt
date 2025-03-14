package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.BookDtoFinal
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.book.ebook.Ebook
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper

class BookMapper (
    private val pdfDownloadStatusHelper: PDFDownloadStatusHelper
){
    fun dtoToDomain(dto: BookDto): Book {
        return Book(
            id = dto._id,
            name = dto.name,
            price = dto.price,
            description = dto.description,
            imageUrls = dto.images,
            exam = dto.targetExam,
            coverUrl = dto.coverImage,
            author = dto.author,
            hardCopyDis = dto.hardcopyDiscount,
            publisher = dto.publisher,
            rate = dto.rate,
            stock = dto.stock,
            isbn = dto.isbn,
            language = dto.language,
            publishDate = dto.publicationDate,
            ebookDis = dto.ebookDiscount,
            noOfPages = dto.pages,
            ebookUrl = dto.eBook
        )
    }

    fun dtoToDomainFinal(dto: BookDtoFinal): Book {
        return Book(
            id = dto._id,
            name = dto.title,
            price = dto.price,
            description = dto.description,
            imageUrls = dto.images,
            exam = dto.targetExam,
            coverUrl = dto.coverImage,
//            author = dto.author,
            hardCopyDis = dto.hardcopyDiscount,
            //publisher = dto.publisher,
            //rate = dto.rating,
            stock = dto.stock,
            //isbn = dto.isbn,
            //language = dto.language,
           // publishDate = dto.publicationDate,
            ebookDis = dto.ebookDiscount,
            //noOfPages = dto.pages,
            ebookUrl = dto.eBook
        )
    }

    fun ebookDtoToDomain(dto: BookDtoFinal): Ebook {
        return Ebook(
            id = dto._id,
            name = dto.title,
            exam = dto.targetExam,
            coverUrl = dto.coverImage,
            author = dto.description,
            url = dto.eBook,
            isDownloaded = pdfDownloadStatusHelper.getDownloadStatus(dto.title)
        )
    }
}