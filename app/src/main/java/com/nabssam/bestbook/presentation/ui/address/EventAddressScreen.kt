package com.nabssam.bestbook.presentation.ui.address

sealed class EventAddressScreen{
    data object LoadAddress : EventAddressScreen()
    data class ToggleAddressFields(val showForm: Boolean) : EventAddressScreen()
    data class ShowError(val message: String) : EventAddressScreen()
    data class AddNewAddress(val address: String) : EventAddressScreen()
    data object FetchFormData : EventAddressScreen()
    data class UpdateFormField(val field: FormField, val value: String) : EventAddressScreen()
    data object SubmitForm : EventAddressScreen()
}

