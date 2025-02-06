package com.example.frontend.domain.model

data class SimpleBarModel(
    val name: String,
    val capacity: Int,
    val address: String,
    val city: String,
    val postalCode: String,
    val planning: List<ScheduleDayModel>,
)