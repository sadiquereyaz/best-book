package com.nabssam.bestbook.data.remote.dto.auth

import com.nabssam.bestbook.domain.model.User

data class SignInResponse(
    val token: String,
    val user: User
)
