package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.ExamDto
import com.nabssam.bestbook.domain.model.Exam

class ExamMapper {
    fun dtoToDomain(dto: ExamDto): Exam {
        return Exam(
            _id = dto._id,
            description = dto.description,
            discount = dto.discount,
            name = dto.name,
            price = dto.price
        )

    }
}