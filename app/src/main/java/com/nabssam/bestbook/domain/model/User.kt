package com.nabssam.bestbook.domain.model

import com.nabssam.bestbook.utils.Constants


data class User(
    val id: String,
    val picUrl: String = "",
    val username: String,
    val email: String = "",
    val isAdmin: Boolean = false,
    val phone: String,
    val currentClass :String = "",
    val schoolName: String = "",
    val accessToken: String = Constants.DEFAULT_ACCESS_TOKEN,
    val refreshToken: String = Constants.DEFAULT_REFRESH_TOKEN,
    val targetExams: List<String>,
    val subscribedEbooks: List<String>
)

enum class Role { ADMIN, MANAGER, USER }
