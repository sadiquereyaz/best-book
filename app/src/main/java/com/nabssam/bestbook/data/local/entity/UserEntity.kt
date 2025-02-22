package com.nabssam.bestbook.data.local.entity

import androidx.room.Entity
import com.nabssam.bestbook.domain.model.Role

@Entity
data class UserEntity(
    val id: String,
    val username: String,
    val email: String,
    val role: Role,
    val phone: String,
    val currentClass: String,
    val targetExams: List<String>,
    val targetYear: String,
    val picUrl: String,
)
