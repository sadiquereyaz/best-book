package com.nabssam.bestbook.domain.model

data class FollowableTopic(
    val topic: Topic,
    val isFollowed: Boolean,
)