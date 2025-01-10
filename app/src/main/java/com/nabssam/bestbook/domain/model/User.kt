package com.nabssam.bestbook.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val createdAt: String
)
