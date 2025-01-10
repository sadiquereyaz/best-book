package com.nabssam.bestbook.presentation.ui.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.model.ShippingDetailModel
import com.nabssam.bestbook.domain.model.UserOld
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddressUiState(
    val orderId: String? = null,
    val addressProcessing: Boolean = false,
    val addressProcessed: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddressViewModel @Inject constructor(
    //private val firebaseRepo: FirebaseRepo
) : ViewModel() {

    private val _addressUiState = MutableStateFlow<AddressUiState>(AddressUiState())
    val addressUiState: StateFlow<AddressUiState>
        get() = _addressUiState

    fun addOrder(shippingDetails: ShippingDetailModel) {
        viewModelScope.launch {
            try {
                _addressUiState.value = AddressUiState(addressProcessing = true)
                //val orderId = firebaseRepo.addOrder(shippingDetails)
                _addressUiState.value =
                    AddressUiState(
                        addressProcessing = false,
                        addressProcessed = true,
                        orderId = /*orderId*/ "ghv"
                    )
            } catch (e: Exception) {
                _addressUiState.value = AddressUiState(addressProcessing = false, error = e.message)
            }
        }
    }

    fun resetAddressUiState() {
        _addressUiState.value = AddressUiState()
    }
}

class FirebaseRepo {
    fun addOrder(shippingDetails: ShippingDetailModel): String? {

        return null
    }

    fun fetchOrders(): Any? {
        return null
    }

    fun user(): UserOld {
        return UserOld()
    }

    fun signOut() {

    }

}
