package com.nabssam.bestbook.data.remote.dto.auth

import com.nabssam.bestbook.domain.model.User

data class AuthData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)