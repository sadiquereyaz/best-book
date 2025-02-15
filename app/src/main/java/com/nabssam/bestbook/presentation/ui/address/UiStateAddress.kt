package com.nabssam.bestbook.presentation.ui.address

sealed class UiStateAddress {
    data object Loading : UiStateAddress()

    sealed class Success : UiStateAddress() {
        data class Addresses(val addresses: List<String>) : Success()

        data class Form(
            val pinCode: String,
            val name: String,
            val phone: String,
            val post: String,
            val district: String,
            val state: String,
            val country: String = "India",
            val isSubmitEnabled: Boolean
        ) : Success()
    }

    data class Error(var message: String) : UiStateAddress()
}
