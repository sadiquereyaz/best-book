package com.nabssam.bestbook.presentation.ui.home

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.usecase.GetAllBannerUseCase
import com.nabssam.bestbook.domain.usecase.GetPyqUseCase
import com.nabssam.bestbook.domain.usecase.book.GetBooksByExamUC
import com.nabssam.bestbook.domain.usecase.exam_std.GetUserTargetsUC
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "VIEW_MODEL_HOME"

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val getBooksByExamUseCase: GetBooksByExamUC,
    private val getTargetExamsUseCase: GetUserTargetsUC,
    private val getAllBannerUseCase: GetAllBannerUseCase,
    private val getPyqUseCase: GetPyqUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StateHomeScreen())
    val state = _state.asStateFlow()

    init {
        onEvent(EventHomeScreen.Initialize)
    }

    fun onEvent(event: EventHomeScreen) {
        when (event) {
            EventHomeScreen.FetchBook -> fetchBooks()
            EventHomeScreen.FetchBanner -> fetchBanners()
            EventHomeScreen.Initialize -> {
                viewModelScope.launch {
                    getUserTargetExams() // Wait for exams to be fetched
                    fetchBooks()    // Fetch books after exams are fetched
                    fetchBanners()  // Fetch banners after exams are fetched
                }
            }
        }
    }

    private suspend fun getUserTargetExams() {
        val targetExamList = getTargetExamsUseCase()
        val randomTargetExam: String? = if (targetExamList.isNotEmpty())
            targetExamList[Random.nextInt(targetExamList.size)]
        else null
        _state.update { it.copy(randomTarget = randomTargetExam) }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            if (state.value.randomTarget == null) return@launch;    // Prevent fetching if randomTargetExam is null. @launch helps in returning from the coroutine block not just from the function
            getBooksByExamUseCase(
                targetExam = state.value.randomTarget ?: "all"
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(fetchingBooks = true) }
                    }

                    is Resource.Success -> {
                        //Log.d("BOOK_DETAIL_VM", "fetchBooks: ${resource.data}")
                        _state.update {
                            it.copy(
                                fetchedBooks = resource.data ?: emptyList(),
                                fetchingBooks = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(fetchingBooks = false, errorBooks = resource.message)
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            getBooksByExamUseCase(
                targetExam = "Free PYQs"
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(fetchingPyq = true) }
                    }

                    is Resource.Success -> {
                        d(TAG, "${resource.data}")
                        _state.update {
                            it.copy(
                                fetchedPyq = resource.data ?: emptyList(),
                                fetchingPyq = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(fetchingPyq = false, errorPyq = resource.message)
                        }
                    }
                }
            }
        }


    }

    private fun fetchBanners() = getAllBannerUseCase.invoke().onEach { resource ->
        when (resource) {
            is Resource.Loading -> _state.update { it.copy(fetchingBanners = true) }
            is Resource.Success -> _state.update {
                it.copy(
                    fetchedBanners = resource.data ?: emptyList(),
                    fetchingBanners = false
                )
            }
            is Resource.Error -> _state.update {
                it.copy(fetchingBanners = false, errorBanners = resource.message)
            }
        }

    }.launchIn(viewModelScope)
}