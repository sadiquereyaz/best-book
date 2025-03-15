package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.PyqDto
import com.nabssam.bestbook.domain.model.Pyq

fun PyqDto.pyqDtoToDomain(): Pyq {
    return Pyq(
        title = this.title,
        link = this.link,
        id = this._id
    )
}