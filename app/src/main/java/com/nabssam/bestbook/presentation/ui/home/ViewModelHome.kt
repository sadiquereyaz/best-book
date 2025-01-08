package com.nabssam.bestbook.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.usecase.GetBannersUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetTargetExamUseCase
import com.nabssam.bestbook.domain.usecase.product.GetBooksByCategoryUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val getBooksByCategoryUseCase: GetBooksByCategoryUseCase,
    getTargetExamUseCase: GetTargetExamUseCase,
    private val getBannersUseCase: GetBannersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StateHomeScreen())
    val state = _state
        .asStateFlow()

    private val targetExam = getTargetExamUseCase().filterNot { it.isEmpty() }

    init {
        onEvent(EventHomeScreen.Retry)
    }

    private fun fetchBanners() {
        viewModelScope.launch {
            targetExam.collect { targetExam ->
                getBannersUseCase(targetExam).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    fetchingBanners = false,
                                    errorBanners = resource.message
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    fetchingBanners = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    fetchedBanners = resource.data ?: emptyList(),
                                    fetchingBanners = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            targetExam.collect { targetExam ->
                getBooksByCategoryUseCase(category = targetExam).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            //Log.d("ViewModelHome", "Book Loading...")
                            _state.update {
                                it.copy(
                                    fetchingBooks = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    fetchedBooks = resource.data ?: emptyList(),
                                    fetchingBooks = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    fetchingBooks = false,
                                    errorBooks = resource.message
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    /*private fun fetchBanners() {
        viewModelScope.launch {
            targetExam.collect { targetExam ->
                getBannersUseCase(targetExam).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = UiStateHome.Idle(
                                StateHomeScreen(
                                    fetchingBanners = false,
                                    errorBanners = resource.message
                                )
                            )
                        }

                        is Resource.Loading -> {
                            _state.update {
                                if (it is UiStateHome.Idle) {
                                    it.copy(
                                        data = it.data.copy(
                                            fetchingBanners = true
                                        )
                                    )
                                } else
                                    it
                            }
                            _state.value = UiStateHome.Idle(
                                StateHomeScreen(
                                    fetchingBanners = true
                                )
                            )
                        }

                        is Resource.Success -> {
                            _state.update { currentState ->
                                if (currentState is UiStateHome.Idle) {
                                    currentState.copy(
                                        currentState.data.copy(
                                            fetchedBanners = resource.data ?: emptyList(),
                                            fetchingBanners = false
                                        )
                                    )
                                } else {
                                    currentState
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    fun onEvent(event: EventHomeScreen) {
        when (event) {
            EventHomeScreen.FetchBook -> fetchBooks()
            EventHomeScreen.FetchBanner -> fetchBanners()
            EventHomeScreen.Retry -> {
                fetchBooks()
                fetchBanners()
            }
        }
    }

}