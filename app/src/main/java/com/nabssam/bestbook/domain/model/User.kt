package com.nabssam.bestbook.domain.model

import com.nabssam.bestbook.data.remote.dto.auth.Avatar

data class User(
    val _id: String,
    val avatar: Avatar,
    val username: String,
    val email: String,
    val role: String,
    val loginType: String,
    val isEmailVerified: Boolean,
    val createdAt: String,
    val updatedAt: String
)