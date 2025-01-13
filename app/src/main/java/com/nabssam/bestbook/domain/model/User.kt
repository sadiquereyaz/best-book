package com.nabssam.bestbook.domain.model

import com.nabssam.bestbook.utils.Constants


data class User(
    val id: String,
    val picUrl: String = "",
    val username: String,
    val email: String = "",
    val role: Role = Role.USER,
    val phone: String,
    val currentClass :String = "",
    val schoolName: String = "",
    val accessToken: String = Constants.DEFAULT_ACCESS_TOKEN,
    val refreshToken: String = Constants.DEFAULT_REFRESH_TOKEN,
    val targetExams: List<TargetExam> = listOf(TargetExam("JEE", 1), TargetExam("NEET", 2)),
)

data class TargetExam(
    val name: String,
    val id: Int
)

enum class Role { ADMIN, MANAGER, USER }
