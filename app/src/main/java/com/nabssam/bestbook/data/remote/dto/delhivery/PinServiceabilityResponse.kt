package com.nabssam.bestbook.data.remote.dto.delhivery


data class PinServiceabilityResponse(
    val delivery_codes: List<DeliveryCode>? = null,
    val Error :String? = null
)

data class DeliveryCode(
    val postal_code: PostalCode
)

data class PostalCode(
    val cash: String,
    val center: List<Center>,
    val city: String,
    val cod: String,
    val country_code: String,
    val covid_zone: Any,
    val district: String,
    val inc: String,
    val is_oda: String,
    val max_amount: Double,
    val max_weight: Double,
    val pickup: String,
    val pin: Int,
    val pre_paid: String,
    val protect_blacklist: Boolean,
    val remarks: String,
    val repl: String,
    val sort_code: String,
    val state_code: String,
    val sun_tat: Boolean
)

data class Center(
    val cn: String,
    val code: String,
    val e: String,
    val s: String,
    val sort_code: String,
    val u: String,
    val ud: String
)