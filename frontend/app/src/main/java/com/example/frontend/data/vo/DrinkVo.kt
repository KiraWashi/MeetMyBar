package com.example.frontend.data.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkVo(
    val id: Int,
    @SerialName("alcohol_degree")
    val alcoholDegree: Double,
    val name: String,
    val brand: String,
    val type: String
)