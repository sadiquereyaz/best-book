package com.nabssam.bestbook.data.mapper

import com.nabssam.bestbook.data.remote.dto.AddressRequest
import com.nabssam.bestbook.presentation.ui.address.UiStateAddress

class AddressMapper {
    fun domainToDto(domain: UiStateAddress.Success.Form): AddressRequest {
        return AddressRequest(
            firstName = domain.firstName,
            lastName = domain.lastName,
            phone = domain.phone,
            address1 = domain.street,
            address2 = domain.locality,
            city = domain.city,
            state = domain.state,
            pincode = domain.pinCode,
            country = domain.country
        )
    }
}
