package com.example.frontend.data.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BasicBarVo(
    val id: Int,
    val name: String,
    val capacity: Int,
    val address: String,
    val city: String,
    @SerialName("postal_code")
    val postalCode: String,
)