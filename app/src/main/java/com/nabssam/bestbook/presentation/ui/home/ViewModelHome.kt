package com.nabssam.bestbook.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.repository.ExamRepository
import com.nabssam.bestbook.domain.usecase.GetBannersUseCase
import com.nabssam.bestbook.domain.usecase.book.GetBooksByCategoryUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetUserTargetsUC
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val getBooksByExamUseCase: GetBooksByCategoryUseCase,
    private val getTargetExamsUseCase: GetUserTargetsUC,
    private val getBannersUseCase: GetBannersUseCase,
    private val examRepository: ExamRepository

) : ViewModel() {

    private val _state = MutableStateFlow(StateHomeScreen())
    val state = _state.asStateFlow()
    private var randomTargetExam: String? = null

    init {
        onEvent(EventHomeScreen.Initialize)
    }

    fun onEvent(event: EventHomeScreen) {
        when (event) {
            EventHomeScreen.FetchBook -> fetchBooks()
            EventHomeScreen.FetchBanner -> fetchBanners()
            EventHomeScreen.Initialize -> {
                viewModelScope.launch {
                    getUserTargets() // Wait for exams to be fetched
                    fetchBooks()    // Fetch books after exams are fetched
                    fetchBanners()  // Fetch banners after exams are fetched
                }
            }
        }
    }

    private suspend fun getUserTargets() {
        val targetExamList = getTargetExamsUseCase()
        randomTargetExam = if (targetExamList.isNotEmpty())
            targetExamList[Random.nextInt(targetExamList.size)]
        else null

        _state.update { it.copy(targetExams = targetExamList) }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            if (randomTargetExam == null) return@launch;    // Prevent fetching if randomTargetExam is null. @launch helps in returning from the coroutine block not just from the function
            getBooksByExamUseCase(targetExam = randomTargetExam ?: "").collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(fetchingBooks = true) }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(fetchedBooks = resource.data ?: emptyList(), fetchingBooks = false)
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
    }


    private fun fetchBanners() {
        viewModelScope.launch {
            if (randomTargetExam == null) return@launch
            getBannersUseCase(randomTargetExam ?: "").collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.update { it.copy(fetchingBanners = true) }
                    is Resource.Success -> _state.update {
                        it.copy(fetchedBanners = resource.data ?: emptyList(), fetchingBanners = false)
                    }
                    is Resource.Error -> _state.update {
                        it.copy(fetchingBanners = false, errorBanners = resource.message)
                    }
                }
            }
        }
    }
}