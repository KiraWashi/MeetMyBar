package com.example.frontend.domain.model

data class DrinkOfBarModel(
    val id: Int,
    val alcoholDegree: String,
    val name: String,
    val brand: String,
    val type: String,
    val price: Double,
    val volume: Double
)