package com.nabssam.bestbook.presentation.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.repository.ExamRepository
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
   private val examRepository: ExamRepository,

) : ViewModel() {
    fun fetch() {
        viewModelScope.launch {
            examRepository.fetchAllExams().collect{resource->
                when(resource){
                    is Resource.Error ->
                        Log.d("ExamViewModel", "fetch: ${resource.message}")
                    is Resource.Loading ->
                        Log.d("ExamViewModel", "fetch")
                    is Resource.Success ->
                        Log.d("ExamViewModel", "fetch: ${resource.data}")
                }

            }



        }
    }
}