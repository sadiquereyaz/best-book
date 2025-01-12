package com.nabssam.bestbook.presentation.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.remote.dto.Chapter
import com.nabssam.bestbook.data.remote.dto.Quize
import com.nabssam.bestbook.data.remote.dto.Subject
import com.nabssam.bestbook.data.repository.ExamRepository
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val examRepository: ExamRepository,

    ) : ViewModel() {


    private val _uiState = MutableStateFlow(examUiState())
    val uiState = _uiState.asStateFlow()
    fun fetchAllExams() {
        viewModelScope.launch {
            examRepository.fetchAllExams().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                exams = emptyList()
                            )
                        }
                        Log.d("ExamViewModel", "fetch: ${resource.message}")
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                exams = emptyList()
                            )
                        }
                        Log.d("ExamViewModel", "fetch")
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                exams = resource.data ?: emptyList()
                            )
                        }
                        Log.d("ExamViewModel", "fetch: ${resource.data}")
                    }

                }

            }


        }
    }

    fun fetchAllSubjects(examId: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                subjects = emptyList()
            )
        }
        viewModelScope.launch {

            examRepository.fetchAllSubjects(examId).collect { resource ->

                when (resource) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                subjects = emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                subjects = emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        Log.d("ExamViewModel", "fetch: ${resource.data}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                subjects = resource.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun fetchAllChapters(subjectId: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                chapters = emptyList()
            )
        }

        viewModelScope.launch {
            examRepository.fetchAllChapters(subjectId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                chapters = emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                chapters = emptyList()

                            )
                        }
                    }

                    is Resource.Success -> {

                        _uiState.update {
                            Log.d("ExamViewModel", "fetchAllChapters: ${resource.data}")
                            it.copy(
                                isLoading = false,
                                chapters = resource.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun fetAllQuizzes(chapterId: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                quizzes = emptyList()
            )
        }

        viewModelScope.launch {
            examRepository.fetchAllQuizzes(chapterId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                quizzes = emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                quizzes = emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        Log.d("ExamViewModel", "fetAllQuizzes: ${resource.data}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                quizzes = resource.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
}

data class examUiState(
    var exams: List<Exam> = emptyList(),
    var isLoading: Boolean = false,
    var subjects: List<Subject> = emptyList(),
    var chapters: List<Chapter> = emptyList(),
    var quizzes: List<Quize> = emptyList()

)