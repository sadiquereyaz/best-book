package com.nabssam.bestbook.domain.model

import com.nabssam.bestbook.data.remote.dto.Rate
import com.nabssam.bestbook.utils.percentOf

data class Book(
    val id: String = "",
    val isbn: String = "",
    val name: String = "",
    val author: String = "",
    val language: String = "",
    val publishDate: String = "",
    val publisher: String = "",
    val price: Int = 0,
    val hardCopyDis: Int = 0,
    val ebookDis: Int? = null,
    val imageUrls: List<String> = emptyList(),
    val coverUrl: String = "",
    val noOfPages: Int = 0,
    val rate: Rate? = null,
    val description: String = "",
    val exam: String = "",
    val stock: Int = 0,
    val ebookUrl: String = "",
){
    val ebookPrice: Int? = ebookDis?.let { price.minus(it.percentOf(price)) }
    val hardcopyPrice: Int = price.minus(hardCopyDis.percentOf(price))
}
