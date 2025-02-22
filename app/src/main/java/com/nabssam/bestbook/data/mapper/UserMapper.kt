package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.auth.UserDto
import com.nabssam.bestbook.domain.model.User

class UserMapper {
    fun dtoToDomain(dto: UserDto): User {
        return User(
            id = dto._id,
            username = dto.username,
            isAdmin = dto.isAdmin,
            picUrl = dto.profilePicture,
            phone = dto.phoneNumber,
            accessToken = dto.accessToken,
            refreshToken = dto.sessionToken,
            targetExams = dto.targetExam,
            email = "dto.email",
            currentClass = dto.currentClass,
            schoolName = "dto.schoolName",
            subscribedEbooks = dto.subscribedEbook,
        )
    }
}