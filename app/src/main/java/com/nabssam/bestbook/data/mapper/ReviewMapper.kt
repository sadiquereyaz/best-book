package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.review.ReviewDto
import com.nabssam.bestbook.domain.model.Review
import com.nabssam.bestbook.utils.parseDateFromIso

class ReviewMapper {
    fun dtoToDomain(dto: ReviewDto): Review {
        return Review(
            reviewId = dto._id,
            profilePicture = dto.profilePicture,
            username = dto.username ?: "",
            description = dto.description,
            date = dto.createdAt.parseDateFromIso(),
            rate = dto.rating
        )
    }
}