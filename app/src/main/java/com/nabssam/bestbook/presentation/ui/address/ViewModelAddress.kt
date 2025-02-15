package com.nabssam.bestbook.presentation.ui.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAddress @Inject constructor(
    private val repository: AddressRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiStateAddress>(UiStateAddress.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(EventAddressScreen.LoadAddress)
    }

    fun onEvent(event: EventAddressScreen) {
        when (event) {
            is EventAddressScreen.AddNewAddress -> TODO()
            is EventAddressScreen.LoadAddress -> fetchAddresses()
            is EventAddressScreen.ShowError -> TODO()
            is EventAddressScreen.ToggleAddressFields -> if (event.showForm)fetchFormData() else fetchAddresses()
            EventAddressScreen.FetchFormData -> TODO()
            EventAddressScreen.SubmitForm -> TODO()
            is EventAddressScreen.UpdateFormField -> onFormFieldUpdated(event.field, event.value)
        }
    }

    private fun fetchFormData() {
        viewModelScope.launch {
            _uiState.value = UiStateAddress.Loading
            try {
                // Simulate fetching initial form data (e.g., from a repository)
                _uiState.value = UiStateAddress.Success.Form(
                    name = "",
                    phone = "",
                    pinCode = "",
                    post = "",
                    district = "",
                    state = "",
                    country = "",
                    isSubmitEnabled = false
                )
            } catch (e: Exception) {
                _uiState.value = UiStateAddress.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun onFormFieldUpdated(field: FormField, value: String) {
        val currentState = _uiState.value as? UiStateAddress.Success.Form ?: return
        val updatedState = when (field) {
            FormField.Name -> currentState.copy(name = value)
            FormField.Pincode -> currentState.copy(pinCode = value)
            FormField.Phone -> currentState.copy(phone = value)
            FormField.Post -> currentState.copy(post = value)
            FormField.District -> currentState.copy(district = value)
            FormField.State -> currentState.copy(state = value)
        }
        _uiState.value = updatedState.copy(isSubmitEnabled = isFormValid(updatedState))
    }

    fun onSubmitForm() {
        val currentState = _uiState.value as? UiStateAddress.Success.Form ?: return
        if (isFormValid(currentState)) {
            viewModelScope.launch {
                // Submit form data (e.g., to a repository)
                //repository.submitForm(currentState)
                // Optionally, navigate to another screen or show a success message
            }
        }
    }

    private fun isFormValid(form: UiStateAddress.Success.Form): Boolean {
        return true
//        form.firstName.isNotBlank() &&
//                form.lastName.isNotBlank() &&
//                form.email.isNotBlank() &&
//                isValidEmail(form.email)
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            try {
                val addresses = repository.fetchAddresses()
//                Log.d("ADDRESS_VM", "fetchAddresses: $addresses")
                _uiState.value = UiStateAddress.Success.Addresses(addresses = addresses )
            } catch (e: Exception) {
                _uiState.value = UiStateAddress.Error(e.message ?: "Unknown error")
            }
        }
    }
    /*
        private suspend fun getUserTargets() {
            val targetExamList = getTargetExamsUseCase()
            val randomTargetExam: String? =  if (targetExamList.isNotEmpty())
                targetExamList[Random.nextInt(targetExamList.size)]
            else null

            _uiState.update { it.copy(randomTarget = randomTargetExam) }
        }

        private fun fetchBooks() {
            viewModelScope.launch {
                if (uiState.value.randomTarget == null) return@launch;    // Prevent fetching if randomTargetExam is null. @launch helps in returning from the coroutine block not just from the function
                getBooksByExamUseCase(targetExam = uiState.value.randomTarget ?: "all").collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(fetchingBooks = true) }
                        }
                        is Resource.Success -> {
                            Log.d("BOOK_DETAIL_VM", "fetchBooks: ${resource.data}")
                            _uiState.update {
                                it.copy(fetchedBooks = resource.data ?: emptyList(), fetchingBooks = false)
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(fetchingBooks = false, errorBooks = resource.message)
                            }
                        }
                    }
                }
            }
        }


        private fun fetchBanners() {
            viewModelScope.launch {
                if (uiState.value.randomTarget == null) return@launch
                getBannersUseCase(uiState.value.randomTarget ?: "").collect { resource ->
                    when (resource) {
                        is Resource.Loading -> _uiState.update { it.copy(fetchingBanners = true) }
                        is Resource.Success -> _uiState.update {
                            it.copy(fetchedBanners = resource.data ?: emptyList(), fetchingBanners = false)
                        }
                        is Resource.Error -> _uiState.update {
                            it.copy(fetchingBanners = false, errorBanners = resource.message)
                        }
                    }
                }
            }
        }*/
}

// Enum to represent form fields
enum class FormField {
    Pincode, Name, Phone, Post, District, State
}