package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.CartItemDTO
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.model.CartItem

class BookMapper {
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
}