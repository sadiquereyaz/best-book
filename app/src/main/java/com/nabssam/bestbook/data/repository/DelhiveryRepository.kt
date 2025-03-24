package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.remote.api.DelhiveryApiService
import com.nabssam.bestbook.domain.repository.DelhiveryRepository
import javax.inject.Inject
private const val TAG = "DELHIVERY_REPO"
class DelhiveryRepoImpl @Inject constructor(
    private val api : DelhiveryApiService
) : DelhiveryRepository {
    override suspend fun checkPincodeServiceability(pincode: String): Result<String> {
        return try {
            val response = api.checkPinCodeServiceability(pincode)
            Log.d(TAG, "checkPincodeServiceability: $response")
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it.delivery_codes?.first()?.postal_code?.city ?: "empty")
                }?: Result.failure(Exception("Pincode is not serviceable"))
            } else Result.failure(Exception("Error occurred while checking pincode"))
        } catch (e:Exception){
            Log.e(TAG, "checkPincodeServiceability: ${e.message}")
            Result.failure(Exception(e.message?: "An error occurred"))
        }
    }

}