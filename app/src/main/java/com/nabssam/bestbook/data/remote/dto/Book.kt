package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val book: BookDto,
    val message: String,
    val success: Boolean,
    @SerializedName("reviewStats")  val bookDetailRateStats: ReviewStats? = null
)

data class PurchasedEbookResponse(
    @SerializedName("ebooks") val books: List<BookDto>,
    val message: String,
    val success: Boolean
)

data class BookListResponse(
    @SerializedName("books") val data: List<BookDto>,
    val message: String,
    val success: Boolean
)

data class BookDto(
    @SerializedName("ISBN") val isbn: String= "",
    val __v: Int,
    val _id: String,
    val author: String? = null,
    @SerializedName("category") val exam: String? = null,
    val coverImage: String? = null,
    val description: String? = null,
    val eBook: String? = null,
    val ebookDiscount: Int? = null,
    val hardcopyDiscount: Int? = null,
    val images: List<String>? = null,
    val isEbookAvailable: Boolean? = null,
    val language: String? = null,
    val pages: Int? = null,
    val price: Int? = null,
    val publicationDate: String? = null,
    val publisher: String? = null,
    @SerializedName("reviewStats") val bookListReviewStats: ReviewStats? = null,
//    val rating: Int? = null,
    val reviews: List<Any>? = null,
//    val rate: Rate? = null,
    val stock: Int? = null,
    val targetExam: String = "",
    @SerializedName("title") val name: String = "",
    val updatedAt: String? = null,
    val createdAt: String? = null,
)

data class ReviewStats(
    val averageRating: Double? = 0.0,
    val reviewCount: Int = 111
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