package com.example.frontend.data.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarVo(
    val id: Int,
    val address: String,
    val name: String,
    val capacity: Int,
    val drinks: List<DrinkVo>,
    val planning: List<ScheduleDayVo>,
    val city: String,
    @SerialName("postal_code")
    val postalCode: String
)