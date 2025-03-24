package com.nabssam.bestbook.domain.repository

interface DelhiveryRepository {
    suspend fun checkPincodeServiceability(pincode:String):Result<String>
}