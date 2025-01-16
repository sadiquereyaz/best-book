package com.nabssam.bestbook.domain.model

data class Book(
    val id: String = "dummy",
    val isbn: String = "dummy",
    val name: String = "AMU Booster 11th Entrance Science Stream Chapterwise with Solution",
    val author: String = "Nawab Saquib Ibrahim, Dr. Maaz Hashmi",
    val language: String = "English",
    val publishDate: String = "10/02/2023",
    val publisher: String = "Sunflower publishing PVT LTD",
    val price: Int = 520,
    val hardCopyDis: Int = 34,
    val ebookDis: Int = 34,
    val imageUrls: List<String> = listOf("https://m.media-amazon.com/images/I/81hupMH+MBL._SL1500_.jpg", "https://m.media-amazon.com/images/I/810I2L5AeqL._SL1500_.jpg", "https://m.media-amazon.com/images/I/71yOH4n3DeL._SL1500_.jpg"),
    val coverUrl: String = "https://m.media-amazon.com/images/I/81F-19PcdaL._AC_SF480,480_.jpg",
    val noOfPages: Int = 127,
    val rating: Float = 4.5f,
    val ratingCount: Int = 123,
    val description: String = "Susan Sontag's essay Against Interpretation is excellent, providing us with a well-needed reminded of the importance of the spiritual effect of the aesthetic: \"in place of a hermeneutics we need an erotics of art\". (I would also recommend the eponymous collection of essays).",
    val exam: String = "AMU-XI Sc",
    val stock: Int = 10,
    val isEbookAvailable: Boolean = true,
)

data class Rate(
    val rate: Double,
    val count: Int,
    val reviews: List<Review>
)

data class Review(
    val userId: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)