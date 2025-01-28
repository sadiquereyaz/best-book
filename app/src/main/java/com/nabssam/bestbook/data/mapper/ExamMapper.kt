package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.AllTargetExamsForClass
import com.nabssam.bestbook.data.remote.dto.ExamDto
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.utils.Standard

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

    fun dtoToDomainFinal(dto: AllTargetExamsForClass): Standard {
        return Standard(
            name = dto.standard,
            exams = dto.targetExam
        )
    }

    fun dtoToDomaint(dto: List<AllTargetExamsForClass>): List<String> {
        var list = mutableListOf<String>()

        return list
    }
}