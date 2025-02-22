package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.ProductDto
import com.nabssam.bestbook.domain.model.Banner

class MiscMapper {
    fun bannerDtoToDomain(dto: ProductDto): Banner {
        return Banner(
            id = (dto.id).toString(),
            imageLink = dto.image,
            redirectLink = dto.image,
            //targetExam = ""
        )
    }

}