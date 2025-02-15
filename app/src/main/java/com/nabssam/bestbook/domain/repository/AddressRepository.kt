package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.dto.Address
import com.nabssam.bestbook.presentation.ui.address.UiStateAddress
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun fetchAddresses(): Flow<Resource<List<Address>>>
    suspend fun deleteAddress(id:String): Flow<Resource<Unit>>
    suspend fun addAddress(address: UiStateAddress.Success.Form): Flow<Resource<Unit>>
}