package com.nabssam.bestbook.presentation.ui.home

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
    var randomTargetExam: String? = null

    init {
        onEvent(EventHomeScreen.Retry)
    }

    fun onEvent(event: EventHomeScreen) {
        when (event) {
            EventHomeScreen.FetchBook -> fetchBooks()
            EventHomeScreen.FetchBanner -> fetchBanners()
            EventHomeScreen.Retry -> {
                fetchAllExams()
                fetchBooks()
                fetchBanners()

            }

            EventHomeScreen.FetchExams -> fetchAllExams()
        }
    }

    private fun fetchBanners() {
        viewModelScope.launch {
            val targetExamList = getTargetExamsUseCase()
            randomTargetExam = targetExamList[Random.nextInt(targetExamList.size)]
            _state.update { it.copy(targetExams = targetExamList) }
            getBannersUseCase(randomTargetExam ?: "").collect { resource ->
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
                                fetchingBanners = false,
                                targetExams = targetExamList
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            getBooksByExamUseCase(targetExam = randomTargetExam ?: "").collect { resource ->
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

    private fun fetchAllExams() {
        /* Log.d("ExamViewModel", "ExamViewModel insidefetch ")
         viewModelScope.launch {
             examRepository.fetchAllExams().collect { resource ->
                 when (resource) {
                     is Resource.Error -> {
                         _state.update {
                             it.copy(
                              examError = resource.message
                             )
                         }
                         Log.d("ExamViewModel", "fetch error: ${resource.message}")
                     }

                     is Resource.Loading -> {
                         _state.update {
                             it.copy(
                                 examLoading = true,
                             )
                         }
                         Log.d("ExamViewModel", "fetch Loading")
                     }

                     is Resource.Success -> {
                         _state.update {
                             it.copy(
                                 fetchedExams = resource.data ?: emptyList()
                             )
                         }
                         Log.d("ExamViewModel", "fetch success: ${resource.data}")
                     }

                 }

             }


         }*/
    }

}