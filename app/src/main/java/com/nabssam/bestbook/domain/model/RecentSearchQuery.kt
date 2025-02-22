//package com.nabssam.bestbook.domain.model
//
//import kotlinx.datetime.Clock
//import kotlinx.datetime.Instant
//
//
//data class RecentSearchQuery(
//    val query: String,
//    val queriedDate: Instant = Clock.System.now(),
//)
//
//fun RecentSearchQueryEntity.asExternalModel() = RecentSearchQuery(
//    query = query,
//    queriedDate = queriedDate,
//)