package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.presentation.ui.book.ebookList.Ebook
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EbookRepository {
    suspend fun fetchEbook(): Flow<Resource<List<Ebook>>>
}