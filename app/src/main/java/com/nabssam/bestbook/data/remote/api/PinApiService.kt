package com.nabssam.bestbook.data.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PinApiService {
    @GET("pincode/{pin}")
    suspend fun getPinDetails(@Path("pin") pin: String): Response<List<PinResponse>>

}

data class PinResponse(
    @SerializedName("Message")
    val message: String,
    @SerializedName("PostOffice")
    val postOffice: List<PostOffice>,
    @SerializedName("Status")
    val status: String
)

data class PostOffice(
    @SerializedName("BranchType")
    val branchType: String,
    @SerializedName("Circle")
    val circle: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("DeliveryStatus")
    val deliveryStatus: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("District")
    val district: String,
    @SerializedName("Division")
    val division: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Region")
    val region: String,
    @SerializedName("State")
    val state: String
)