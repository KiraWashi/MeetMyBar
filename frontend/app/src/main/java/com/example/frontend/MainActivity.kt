package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.frontend.core.appModule
import com.example.frontend.presentation.feature.settings.SettingsViewModel
import com.example.frontend.presentation.navigation.SetupNavGraph
import com.example.frontend.ui.theme.FrontendTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(1000)
            keepSplash = false
        }

        enableEdgeToEdge()
        setContent {
            FrontendTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navHostController = rememberNavController()
                    SetupNavGraph(modifier = Modifier.padding(innerPadding), navHostController = navHostController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FrontendTheme {
        Greeting("Android")
    }
}