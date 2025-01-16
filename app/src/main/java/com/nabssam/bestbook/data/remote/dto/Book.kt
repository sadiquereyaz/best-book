package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val books: List<BookDto>,
    val message: String,
    val success: Boolean
)

data class BookDto(

    val isbn: String= "111-222-222-444",
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
    val rate: Rate,
    val stock: Int,
    val targetExam: String,
    @SerializedName("title")
    val name: String,
    val updatedAt: String,
    val createdAt: String,
)

data class Rate(
    val rate: Double = 3.5,
    val count: Int = 122,
    val reviews: List<Review> = listOf(Review(), Review(), Review())
)

data class Review(
    val id: String = "1",
    val userId: String = "1",
    val rating: Int = 4,
    val comment: String = "This is a good book",
    val createdAt: String = "14/04/2022"
)