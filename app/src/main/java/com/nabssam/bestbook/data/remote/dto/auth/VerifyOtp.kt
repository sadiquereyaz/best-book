package com.nabssam.bestbook.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    val otp: Int,
    @SerializedName("phoneNumber") val phone: String
)
data class VerifyOtpResponse(
    @SerializedName("message") val message: String
)