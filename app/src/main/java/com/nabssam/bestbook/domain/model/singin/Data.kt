package com.nabssam.bestbook.domain.model.singin

import com.nabssam.bestbook.domain.model.User

data class Data(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)