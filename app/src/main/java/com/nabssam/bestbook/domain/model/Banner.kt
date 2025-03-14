package com.nabssam.bestbook.domain.model

data class Banner(
    val imageLink: String,
    val redirectLink: String,
    val id: String = ""     // todo: remove
)