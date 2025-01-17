package com.example.frontend.domain.model

data class DrinkModel(
    val id: Int,
    val alcoholDegree: Double,
    val name: String,
    val brand: String,
    val type: String
)