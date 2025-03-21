package com.nabssam.bestbook.utils

import com.nabssam.bestbook.domain.model.Review

fun Review.setOwner(username: String): Review = this.copy(isOwner = username == this.username)