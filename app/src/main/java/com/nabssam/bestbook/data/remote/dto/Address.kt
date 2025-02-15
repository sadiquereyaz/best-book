package com.nabssam.bestbook.data.remote.dto

data class AddressRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val address1: String,
    val address2: String?, // Making address2 optional as it might not always be present
    val city: String,
    val state: String,
    val pincode: String,
    val country: String = "India"
)

data class AddressResponse(
    val addresses: List<Address>,
    val message: String,
    val success: Boolean
)

data class Address(
    val __v: Int,
    val _id: String,
    val address1: String,
    val address2: String,
    val city: String,
    val country: String,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val maxAddress: Int,
    val phone: String,
    val pincode: String,
    val state: String,
    val updatedAt: String,
    val userId: String
)