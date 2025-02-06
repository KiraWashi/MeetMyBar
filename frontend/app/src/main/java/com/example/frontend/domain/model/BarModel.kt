package com.example.frontend.domain.model

data class BarModel(
    val id: Int,
    val address: String,
    val name: String,
    val capacity: Int,
    val drinks: List<DrinkModel>,
    val planning: List<ScheduleDayModel>,
    val city: String,
    val postalCode: String
)
