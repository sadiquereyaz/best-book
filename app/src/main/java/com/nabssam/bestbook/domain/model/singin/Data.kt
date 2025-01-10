package com.nabssam.bestbook.domain.model.singin

data class Data(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)