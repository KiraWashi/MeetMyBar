package com.example.frontend.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.frontend.data.datastore.ThemeMode
import com.example.frontend.presentation.feature.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

private val DarkColorScheme = darkColorScheme(
    primary = InversePrimaryLight,
    inversePrimary = InversePrimaryDark,
    secondary = SpritzColorDark,
    tertiary = Spritz2ColorDark,
    secondaryContainer = SecondaryContainerDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
)

private val LightColorScheme = lightColorScheme(
    primary = InversePrimaryDark,
    inversePrimary = InversePrimaryLight,
    secondary = SpritzColorLight,
    tertiary = Spritz2ColorLight,
    secondaryContainer = SecondaryContainerLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
)

@Composable
fun FrontendTheme(
    content: @Composable () -> Unit
) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val settingsViewModelState = viewModel.settingsViewModelState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getThemeMode()
    }

    val isDarkTheme = when (settingsViewModelState.value.themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.AUTO -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}