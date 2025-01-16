package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.domain.model.Book

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
            // rate = Rate(dto.rating.toDouble(), dto.reviews.size),
            stock = dto.stock,
            isbn = dto.isbn,
            language = dto.language,
            publishDate = dto.publicationDate,
            ebookDis = dto.ebookDiscount,
            noOfPages = dto.pages,
            ebook = dto.eBook
        )
    }

    // Domain to Entity
    fun domainToEntity(domain: Book): CartItemEntity {
        return CartItemEntity(
            productId = domain.id,
            name = domain.name,
            coverImage = domain.imageUrls[0],
            price = domain.price,
            disPer = domain.hardCopyDis,
            inStock = domain.stock != 0,
            quantity = 1,
        )
    }
}