package com.example.frontend.domain.model

data class PhotoModel(
    val id: Int,
    val description: String,
    val mainPhoto: Boolean,
    val imageData: String
)