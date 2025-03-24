package com.nabssam.bestbook.presentation.ui.address

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.repository.AddressRepository
import com.nabssam.bestbook.domain.usecase.delhivery.PincodeServiceabilityUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
private const val TAG = "VIEWMODEL_ADDRESS"
@HiltViewModel
class ViewModelAddress @Inject constructor(
    private val repository: AddressRepository,
    private val pinServiceabilityUseCase: PincodeServiceabilityUseCase

) : ViewModel() {

    private var _uiState = MutableStateFlow<UiStateAddress>(UiStateAddress.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        //onEvent(EventAddressScreen.LoadAddress)
        onEvent(EventAddressScreen.Deliverable("400064"))
        _uiState.value = UiStateAddress.Success.Form()
    }

    fun onEvent(event: EventAddressScreen) {
        when (event) {
            is EventAddressScreen.LoadAddress -> fetchAddresses()
            is EventAddressScreen.AddNewAddress -> TODO()
            is EventAddressScreen.DeleteAddress -> deleteAddress(event.id)
            is EventAddressScreen.ShowError -> TODO()
            is EventAddressScreen.ToggleAddressFields -> if (event.showForm) fetchFormData() else fetchAddresses()
            is EventAddressScreen.FetchFormData -> TODO()
            is EventAddressScreen.SubmitForm -> onSubmitForm()
            is EventAddressScreen.UpdateFormField -> onFormFieldUpdated(event.field, event.value)
            is EventAddressScreen.Deliverable -> {
                isDeliverable(pincode = event.pincode)
            }
        }
    }

    private fun isDeliverable(pincode: String) = pinServiceabilityUseCase.invoke(pincode).onEach { resource ->
        Log.d(TAG, "isDeliverable: $resource")
        when(resource){
            is Resource.Loading -> {
                onFormFieldUpdated(field = FormField.PinCodeChecking, value = true.toString())

            }
            is Resource.Error -> {
                onFormFieldUpdated(
                    field = FormField.PinError,
                    value = resource.message ?: "Error occurred while checking"
                )
                onFormFieldUpdated(field = FormField.PinCodeChecking, value = false.toString())
                Log.e(TAG, "isDeliverable error: ${resource.message}")
            }
            is Resource.Success -> {
                onFormFieldUpdated(
                    field = FormField.PinSupportingText,
                    value = "Pincode is deliverable"
                )
                onFormFieldUpdated(
                    field = FormField.City,
                    value = resource.message ?: "Error occurred while checking"
                )
                onFormFieldUpdated(field = FormField.PinCodeChecking, value = false.toString())
                onFormFieldUpdated(field = FormField.Deliverable, value = true.toString())

            }
        }
    }.launchIn(viewModelScope)

    private fun deleteAddress(id: String) {
        viewModelScope.launch {
            val initialAddresses = (_uiState.value as? UiStateAddress.Success.Addresses)
            repository.deleteAddress(id).collect {
                when (it) {
                    is Resource.Loading -> _uiState.value = UiStateAddress.Loading
                    is Resource.Error -> _uiState.value =
                        UiStateAddress.Error(it.message ?: "Unknown error")

                    is Resource.Success -> {
                        val updatedAddresses = initialAddresses?.addresses?.filter { address ->
                            address._id != id }
                        updatedAddresses?.let { _uiState.value = UiStateAddress.Success.Addresses(it) }
                    }
                }
            }
        }
    }

    private fun fetchFormData() {
        viewModelScope.launch {
            _uiState.value = UiStateAddress.Loading
            try {
                // Simulate fetching initial form data (e.g., from a repository)
                _uiState.value = UiStateAddress.Success.Form(
                    /*firstName = "",
                    lastName = "",
                    phone = "",
                    pinCode = "",
                    street = "",
                    city = "",
                    state = "",
                    country = "",
                    locality = "",
                    isSubmitEnabled = false,*/
                 pinCode = "110025",
                 firstName = "John",
                 lastName = "Doe",
                 phone = "9876543210",
                 street = "123 Main Street",
                 locality = "Central Park",
                 city = "New Delhi",
                 state = "Delhi",
                 country = "India",
                 isSubmitEnabled = true,
                 isDeliverable = true,
                )
            } catch (e: Exception) {
                _uiState.value = UiStateAddress.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun onFormFieldUpdated(field: FormField, value: String) {
        val currentState = _uiState.value as? UiStateAddress.Success.Form ?: return
        val updatedState = when (field) {
            FormField.FirstName -> currentState.copy(firstName = value)
            FormField.LastName -> currentState.copy(lastName = value)
            FormField.Pincode -> currentState.copy(pinCode = value)
            FormField.Phone -> currentState.copy(phone = value)
            FormField.Locality -> currentState.copy(locality = value)
            FormField.Street -> currentState.copy(street = value)
            FormField.City -> currentState.copy(city = value)
            FormField.State -> currentState.copy(state = value)
            FormField.PinSupportingText -> currentState.copy(pinCodeSupportingText = value)
            FormField.PinError -> currentState.copy(pinCodeError = value)
            FormField.PinCodeChecking -> currentState.copy(isPinCodeChecking = value.toBoolean())
            FormField.Deliverable -> currentState.copy(isDeliverable = value.toBoolean())
        }
        _uiState.value = updatedState.copy(isSubmitEnabled = isFormValid(updatedState))
    }

    private fun onSubmitForm() {
        val currentState = _uiState.value as? UiStateAddress.Success.Form ?: return
        if (isFormValid(currentState)) {
            viewModelScope.launch {
                // Submit form data (e.g., to a repository)
                repository.addAddress(currentState).collect{
                    when(it){
                        is Resource.Loading -> _uiState.value = UiStateAddress.Loading
                        is Resource.Error -> _uiState.value = UiStateAddress.Error(it.message ?: "Unknown error")
                        is Resource.Success -> fetchAddresses()
                    }
                }
                // Optionally, navigate to another screen or show a success message
            }
        }
    }

    private fun isFormValid(form: UiStateAddress.Success.Form): Boolean {
        return form.firstName.isNotBlank() &&
                form.pinCode.isNotBlank() &&
                form.street.isNotBlank() &&
                form.state.isNotBlank() &&
                form.locality.isNotBlank() &&
                form.phone.isNotBlank() &&
                form.lastName.isNotBlank() &&
                form.country.isNotBlank() &&
                form.isDeliverable &&
                form.city.isNotBlank()
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            try {
                repository.fetchAddresses().collect {
                    when (it) {
                        is Resource.Loading -> _uiState.value = UiStateAddress.Loading
                        is Resource.Error -> _uiState.value = UiStateAddress.Error(it.message ?: "Unknown error")
                        is Resource.Success -> {
                            _uiState.value =
                                UiStateAddress.Success.Addresses(it.data ?: emptyList())
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiStateAddress.Error(e.message ?: "Unknown error")
            }
        }
    }
}

