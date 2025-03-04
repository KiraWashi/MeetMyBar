package com.example.frontend.data.vo

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDayVo(
    val opening: String,
    val closing: String,
    val day: String
)
