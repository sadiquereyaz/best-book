package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.BannerDto
import com.nabssam.bestbook.domain.model.Banner

fun BannerDto.bannerDtoToDomain(): Banner {
    return Banner(
        imageLink = this.imageUrl,
        redirectLink = this.redirectLink,
        id = this._id
    )
}