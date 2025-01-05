package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.local.entity.ProductEntity
import com.nabssam.bestbook.data.remote.dto.ProductDto
import com.nabssam.bestbook.domain.model.Product

class ProductMapper {
    fun dtoToDomain(dto: ProductDto): Product {
        return Product(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            price = dto.price,
            imageUrl = dto.imageUrl,
            category = dto.category,
            rating = dto.rating,
            stock = dto.stock
        )
    }

    fun domainToDto(domain: Product): ProductDto {
        return ProductDto(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            price = domain.price,
            imageUrl = domain.imageUrl,
            category = domain.category,
            rating = domain.rating,
            stock = domain.stock
        )
    }
    // Entity to Domain
    fun entityToDomain(entity: ProductEntity): Product {
        return Product(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            imageUrl = entity.imageUrl,
            category = entity.category,
            rating = entity.rating,
            stock = entity.stock
        )
    }

    // Domain to Entity
    fun domainToEntity(domain: Product): ProductEntity {
        return ProductEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            price = domain.price,
            imageUrl = domain.imageUrl,
            category = domain.category,
            rating = domain.rating,
            stock = domain.stock
        )
    }
}