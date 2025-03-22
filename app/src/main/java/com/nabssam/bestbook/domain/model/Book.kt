package com.nabssam.bestbook.domain.model

import com.nabssam.bestbook.data.remote.dto.Rate
import com.nabssam.bestbook.data.remote.dto.ReviewStats
import com.nabssam.bestbook.utils.percentOf

data class Book(
    val id: String = "",
    val isbn: String? = null,
    val name: String = "",
    val author: String? = null,
    val language: String? = null,
    val publishDate: String? = null,
    val publisher: String? = null,
    val price: Int = 0,
    val hardCopyDis: Int = 0,
    val ebookDis: Int? = null,
    val imageUrls: List<String> = emptyList(),
    val coverUrl: String? = null,
    val noOfPages: Int?=null,
    //val rate: ReviewStats? = null,
    val averageRate: Double? = null,
    val reviewCount: Int? = null,
    val description: String? = null,
    val exam: String? = null,
    val stock: Int = 0,
    val ebookUrl: String? = null,
){
    val ebookPrice: Int? = ebookDis?.let { price.minus(it.percentOf(price)) }
    val hardcopyPrice: Int = price.minus(hardCopyDis.percentOf(price))
}

