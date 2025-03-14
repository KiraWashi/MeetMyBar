package com.example.frontend.domain.model

data class BasicBarModel(
    val id : Int,
    val name: String,
    val capacity: Int,
    val address: String,
    val city: String,
    val postalCode: String,
)