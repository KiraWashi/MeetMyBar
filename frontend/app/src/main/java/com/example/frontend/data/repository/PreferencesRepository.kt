package com.example.frontend.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.frontend.data.datastore.ThemeMode
import com.example.frontend.data.datastore.ThemePreferences
import com.example.frontend.domain.repository.PreferencesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository(
    private val dataStore: DataStore<Preferences>
): PreferencesRepositoryInterface {
    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    override fun getThemeMode(): Flow<ThemePreferences> = dataStore.data.map { preferences ->
        ThemePreferences(
            themeMode = preferences[THEME_MODE_KEY]?.let { ThemeMode.valueOf(it) } ?: ThemeMode.AUTO
        )
    }

    override suspend fun updateThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
}