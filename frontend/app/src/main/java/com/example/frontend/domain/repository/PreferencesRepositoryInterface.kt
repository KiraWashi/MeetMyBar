package com.example.frontend.domain.repository

import com.example.frontend.data.datastore.ThemeMode
import com.example.frontend.data.datastore.ThemePreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepositoryInterface {
    fun getThemeMode(): Flow<ThemePreferences>
    suspend fun updateThemeMode(themeMode: ThemeMode)
}

