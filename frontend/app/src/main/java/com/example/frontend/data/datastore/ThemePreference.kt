package com.example.frontend.data.datastore

enum class ThemeMode {
    LIGHT, DARK, AUTO
}

data class ThemePreferences(
    val themeMode: ThemeMode = ThemeMode.AUTO
)