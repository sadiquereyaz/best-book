package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookResponseFinal(
    val book: BookDtoFinal,
    val message: String,
    val success: Boolean
)

data class BookListResponseFinal(
    val books: List<BookDtoFinal>,
    val message: String,
    val success: Boolean
)

data class BookDtoFinal(
    val __v: Int,
    val _id: String,
    val coverImage: String,
    val createdAt: String,
    val description: String,
    val eBook: String,
    val ebookDiscount: Int,
    val hardcopyDiscount: Int,
    val images: List<String>,
    val isEbookAvailable: Boolean,
    val price: Int,
    val rating: Int,
    val reviews: List<Any>,
    val reviewsId: List<Any>,
    val stock: Int,
    val targetExam: String,
    val title: String,
    val updatedAt: String
)

data class PurchasedEbookResponse(
    @SerializedName("ebooks") val data: List<BookDtoFinal>,
    val message: String,
    val success: Boolean
)

data class BookListResponse(
    @SerializedName("books") val data: List<BookDto>,
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
    val rate: Rate? = null,
    val stock: Int,
    val targetExam: String,
    @SerializedName("title")
    val name: String,
    val updatedAt: String,
    val createdAt: String,
)

data class Rate(
    val points: Double = 3.5,
    val count: Int = 122,
    val reviews: List<Review>? = null
)

data class Review(
    val id: String = "1",
    val username: String = "Rafique",
    val reviewDp: String? = null,
    val rating: Int = 4,
    val comment: String? = null,
    val createdAt: String = "14/04/2022"
)