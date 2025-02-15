package com.nabssam.bestbook.presentation.ui.address

import com.nabssam.bestbook.data.remote.dto.Address

sealed class UiStateAddress {
    data object Loading : UiStateAddress()

    sealed class Success : UiStateAddress() {
        data class Addresses(val addresses: List<Address>) : Success()

        data class Form(
            val pinCode: String,
            val firstName: String,
            val lastName: String,
            val phone: String,
            val street: String,
            val locality: String,
            val city: String,
            val state: String,
            val country: String = "India",
            val isSubmitEnabled: Boolean,
            val isDeliverable: Boolean = false,
        ) : Success()
    }

    data class Error(var message: String) : UiStateAddress()
}
