package com.example.frontend.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDayModel(
    val opening: String,
    val closing: String,
    val day: String
)