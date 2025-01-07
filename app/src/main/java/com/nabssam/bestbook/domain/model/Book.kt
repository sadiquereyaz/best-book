package com.nabssam.bestbook.domain.model

data class Book(
    val id: String = "dummy",
    val name: String = "AMU Booster 11th Entrance Science Stream Chapterwise with Solution",
    val author: String = "Nawab Saquib Ibrahim, Dr. Maaz Hashmi",
    val language: String = "English",
    val publishDate: String = "10/02/2023",
    val publisher: String = "Sunflower publishing PVT LTD",
    val price: Int = 520,
    val discount: Int = 34,
    val imageUrls: List<String> = listOf("https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/covers/3.png", "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/covers/2.png"),
    val noOfPages: Int = 127,
    val rating: Float = 4.5f,
    val ratingCount: Int = 123,
    val description: String = "Susan Sontag's essay Against Interpretation is excellent, providing us with a well-needed reminded of the importance of the spiritual effect of the aesthetic: \"in place of a hermeneutics we need an erotics of art\". (I would also recommend the eponymous collection of essays).",
    val category: String = "AMU-XI Sc",
    val stock: Int = 10
)
