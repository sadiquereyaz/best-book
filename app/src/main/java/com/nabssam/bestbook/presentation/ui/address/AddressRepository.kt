package com.nabssam.bestbook.presentation.ui.address

import kotlinx.coroutines.delay
import javax.inject.Inject

interface AddressRepository {
    suspend fun fetchAddresses(): List<String>
    suspend fun addAddress(address: String)
}

class AddressRepositoryImpl @Inject constructor():AddressRepository{
    override suspend fun fetchAddresses(): List<String> {
        delay(1000) // Simulating network delay
        return listOf(
            "Block 3, FRK Boys Hostel, Jamia Millia Islamia, 220123, New Delhi, Delhi",
            "Block 21, New Boys Hostel, Jamia Millia Islamia, 220123, New Delhi, Delhi",
        )
    }

    override suspend fun addAddress(address: String) {
        TODO("Not yet implemented")
    }

}