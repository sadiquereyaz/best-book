package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.local.entity.BookEntity
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.ProductFreeApi
import com.nabssam.bestbook.data.remote.dto.Rate
import com.nabssam.bestbook.domain.model.Book
import kotlin.random.Random

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
    fun productDtoToDomain(dto: ProductFreeApi): Book {
        return Book(
            id = dto._id,
            name = dto.name,
            price = dto.price,
            description = dto.description,
            imageUrls = dto.subImages.map { it.url },
            category = dto.category,
            author = dto.owner,
            discount = Random.nextInt(100),
            publisher = dto.category,
            rating = Random.nextFloat()*10,
            stock = dto.stock
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
    fun domainToEntity(domain: Book): CartItemEntity {
        return CartItemEntity(
            productId = domain.id,
            name = domain.name,
            coverImage = domain.imageUrls[0],
            price = domain.price,
            disPer = domain.discount,
            inStock = domain.stock != 0,
            quantity = 1,
        )
    }
}