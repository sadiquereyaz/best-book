package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val books: List<BookDto>,
    val message: String,
    val success: Boolean
)

data class BookDto(
    val ISBN: String,
    val __v: Int,
    val _id: String,
    val author: String,
    @SerializedName("category") val exam: String,
    val coverImage: String,
    val description: String,
    val eBook: String,
    val ebookDiscount: Int,
    val hardcopyDiscount: Int,
    val images: List<String>,
    val isEbookAvailable: Boolean,
    val language: String,
    val pages: Int,
    val price: Int,
    val publicationDate: String,
    val publisher: String,
    val rating: Int,
    val reviews: List<Any>,
    val reviewsId: List<Any>,
    val stock: Int,
    val targetExam: String,
    @SerializedName("title")
    val name: String,
    val updatedAt: String,
    val createdAt: String,
)

