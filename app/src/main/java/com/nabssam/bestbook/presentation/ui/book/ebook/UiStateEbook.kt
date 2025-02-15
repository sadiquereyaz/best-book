package com.nabssam.bestbook.presentation.ui.book.ebook

data class UiStateEbook(
    val error: String? = null,
    val isLoading: Boolean = false,
    val ebookList: List<Ebook> = emptyList()
)

sealed class EventEbook{
    data object FetchEbook: EventEbook()
    data object Retry: EventEbook()
}