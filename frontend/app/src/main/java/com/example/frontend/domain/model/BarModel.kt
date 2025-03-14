package com.example.frontend.domain.model

data class BarModel(
    val id: Int,
    val address: String,
    val name: String,
    val capacity: Int,
    val drinks: List<DrinkOfBarModel>,
    val planning: List<ScheduleDayModel>,
    val city: String,
    val postalCode: String,
    val latitude: Double? = null,
    val longitude: Double? = null
)
