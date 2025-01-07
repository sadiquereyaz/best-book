package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.local.entity.BookEntity
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.Rate
import com.nabssam.bestbook.domain.model.Book

class BookMapper {
    fun dtoToDomain(dto: BookDto): Book {
        return Book(
            id = dto.id,
            name = dto.name,
            price = dto.price.toInt(),
            description = dto.description,
            imageUrls = listOf(dto.image),
            category = dto.category
        )
    }

    fun domainToDto(domain: Book): BookDto {
        return BookDto(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            price = domain.price.toDouble(),
            image = domain.imageUrls[0],
            category = domain.category,
            rating = Rate(domain.rating.toDouble(), 5)
        )
    }
    // Entity to Domain
    fun entityToDomain(entity: BookEntity): Book {
        return Book(
            id = entity.id,
            name = entity.name,
            imageUrls = listOf(entity.imageUrl),
            category = entity.category,
        )
    }

    // Domain to Entity
    fun domainToEntity(domain: Book): BookEntity {
        return BookEntity(
            id = domain.id,
            name = domain.name,
            imageUrl = domain.imageUrls[0],
            category = domain.category,
            rating = domain.rating,
            rateCount = domain.ratingCount
        )
    }
}