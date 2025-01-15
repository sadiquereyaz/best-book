package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.local.entity.BookEntity
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.remote.dto.BookDto
import com.nabssam.bestbook.data.remote.dto.ProductFreeApi
import com.nabssam.bestbook.data.remote.dto.Rate
import com.nabssam.bestbook.data.remote.dto.auth.SignInResponse
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.model.Role
import com.nabssam.bestbook.domain.model.User
import kotlin.random.Random

class UserMapper {
    fun dtoToDomain(dto: SignInResponse): User {
        return User(
            id = dto._id,
            username = dto.username,
            isAdmin = dto.isAdmin,
            picUrl = dto.profilePicture,
            phone = dto.phoneNumber,
            accessToken = dto.currentToken,
            refreshToken = dto.sessionToken,
            targetExams = dto.targetExam,
            email = "dto.email",
            currentClass = dto.currentClass,
            schoolName = "dto.schoolName",
            subscribedEbooks = dto.subscribedEbook,
        )
    }

/*    fun domainToDto(domain: User): SignUpRequest {
        return SignUpRequest(
            currentClass = TODO(),
            password = TODO(),
            phoneNumber = TODO(),
            targetExam = TODO(),
            targetYear = TODO(),
            username = TODO()
        )
    }*/

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