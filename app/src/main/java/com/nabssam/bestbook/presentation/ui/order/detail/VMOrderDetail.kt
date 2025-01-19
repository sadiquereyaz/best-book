package com.nabssam.bestbook.presentation.ui.order.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOrderDetail @Inject constructor(
    private val orderRepository: OrderRepositoryMain,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderId: String = checkNotNull(savedStateHandle["orderId"])
    private val _uiState: MutableStateFlow<UiStateOrderDetail> =
        MutableStateFlow(UiStateOrderDetail.Loading)
    val uiState: StateFlow<UiStateOrderDetail> = _uiState.asStateFlow()

    init {
        fetchOrderDetail(orderId)
    }

    fun onEvent(event: EventOrderDetail) {
        when (event) {
            EventOrderDetail.Initialise -> fetchOrderDetail(orderId)
            EventOrderDetail.ReturnClicked -> {}
            EventOrderDetail.ChatClicked -> {}
            EventOrderDetail.RateClicked -> {}
        }
    }

    private fun fetchOrderDetail(orderId: String) {
        viewModelScope.launch {
            _uiState.emit(UiStateOrderDetail.Loading)
            println("orderId : $orderId")
            try {
                val orderDetail = orderRepository.getOrderById(orderId = orderId)
                _uiState.emit(UiStateOrderDetail.Success(orderDetail))
            } catch (e: Exception) {
                _uiState.emit(UiStateOrderDetail.Error)
            }
        }
    }
}
