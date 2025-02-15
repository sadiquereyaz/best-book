package com.nabssam.bestbook.presentation.ui.book.ebook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelEbook @Inject constructor(
    private val repository: EbookRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiStateEbook())
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(EventEbook.FetchEbook)
    }

    fun onEvent(event: EventEbook){
        when(event){
            is EventEbook.FetchEbook -> fetchEbook()
            is EventEbook.Retry -> fetchEbook()
        }
    }

    private fun fetchEbook() {
        viewModelScope.launch {
            repository.fetchEbook().collect{
                when(it){
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            ebookList = it.data ?: emptyList()
                        )
                        Log.d("TAG", "fetchEbook: ${it.data}")
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

}