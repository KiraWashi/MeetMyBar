package com.example.frontend.data.vo

import kotlinx.serialization.Serializable

@Serializable
data class PhotoVo(
    val id: Int,
    val description: String,
    val mainPhoto: Boolean,
    val imageData: String,
)