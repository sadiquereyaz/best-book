package com.nabssam.bestbook.data.remote.dto.auth

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

data class SignInRequest(
    val username: String,
    val password: String
)

data class SignInResponse(
    val success: Boolean,
    val message: String,
    val user: UserDto
)

// SignIn Response
data class UserDto(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val currentClass: String,
    val isAdmin: Boolean,
    val phoneNumber: String,
    val profilePicture: String,
    val accessToken: String,
    val password: String? = null,   // todo: this should not be returned
    val sessionToken: String,
    val subscribedEbook: List<String>,
    val targetExam: List<String>,
    val targetYear: List<String>,
    val updatedAt: String,
    val username: String,
    val sessionId: String
)

/*
val json: Json =

{"success":true,"user":
    {
        "_id":"67ba0e38be16071ac50c3afb",
        "username":"sadique",
        "phoneNumber":"8287895302",
        "currentClass":"10th Grade",
        "targetExam":["JEE", "NEET"],
        "targetYear":["2026"],
        "profilePicture":"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
        "subscribedEbook":[],
        "isAdmin":false,
        "accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YmEwZTM4YmUxNjA3MWFjNTBjM2FmYiIsImlzQWRtaW4iOmZhbHNlLCJzZXNzaW9uSWQiOiJiY2Q1MjZhZi1lOWUxLTQzMGMtYTUzOS1hZWI2NWFmMGYzYWMiLCJpYXQiOjE3NDAyNDY2NjEsImV4cCI6MTc0MTExMDY2MX0.jB--9YkOMih7i6CoGEFMDXD_ICXGA6Y7AJwL-1F_hc4",
        "sessionToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YmEwZTM4YmUxNjA3MWFjNTBjM2FmYiIsImlzQWRtaW4iOmZhbHNlLCJzZXNzaW9uSWQiOiJiY2Q1MjZhZi1lOWUxLTQzMGMtYTUzOS1hZWI2NWFmMGYzYWMiLCJpYXQiOjE3NDAyNDY2NjEsImV4cCI6MTc0MTExMDY2MX0.jB--9YkOMih7i6CoGEFMDXD_ICXGA6Y7AJwL-1F_hc4",
        "sessionId":"bcd526af-e9e1-430c-a539-aeb65af0f3ac", "addresses":[], "createdAt":"2025-02-22T17:49:44.995Z",
        "updatedAt":"2025-02-22T17:51:01.367Z", "__v":0
    },
    "message":"Sign in successful"
}

*/
