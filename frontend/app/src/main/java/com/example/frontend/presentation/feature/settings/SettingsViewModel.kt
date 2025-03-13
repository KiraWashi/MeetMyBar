package com.example.frontend.presentation.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.datastore.ThemeMode
import com.example.frontend.domain.repository.PreferencesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsViewModelState(
    var themeMode: ThemeMode = ThemeMode.AUTO
)

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepositoryInterface
): ViewModel() {

    private val _settingsViewModelState = MutableStateFlow(SettingsViewModelState())
    val settingsViewModelState = _settingsViewModelState.asStateFlow()

    fun getThemeMode() {
        viewModelScope.launch {
            preferencesRepository.getThemeMode().collect { themeMode ->
                _settingsViewModelState.update {
                    it.copy(
                        themeMode = themeMode.themeMode
                    )
                }
            }
        }
    }

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            preferencesRepository.updateThemeMode(themeMode)
            _settingsViewModelState.update {
                it.copy(
                    themeMode = themeMode
                )
            }
        }
    }
}