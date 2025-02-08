package com.example.frontend.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frontend.presentation.splashscreen.SplashScreenBeer
import com.example.frontend.presentation.bar.PageBar
import com.example.frontend.presentation.biere.AddBiere
import com.example.frontend.presentation.biere.ListBiere
import com.example.frontend.presentation.home.HomeScreen
import com.example.frontend.presentation.biere.TestApiScreen

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SetupNavGraph(
    modifier: Modifier,
    navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplachScreenBeer.route
    ) {
        composable(
            route = Screen.SplachScreenBeer.route
        ) {
            SplashScreenBeer(
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.PageBar.route
        ) {
            PageBar(
                modifier = modifier,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.TestApiScreen.route
        ) {
            TestApiScreen()
        }
        composable(
            route = Screen.ListBiere.route
        ) {
            ListBiere(
                navHostController = navHostController,
                modifier = modifier
            )
        }
        composable(
            route = Screen.AddBiere.route
        ) {
            AddBiere(
                navHostController = navHostController,
                modifier = modifier
            )
        }
    }
}

sealed class Screen(val route: String) {
    object SplachScreenBeer : Screen("SplashScreenBeer")
    object PageBar : Screen("PageBar")
    object HomeScreen : Screen("HomeScreen")
    object ListBiere : Screen("ListBiere")
    object AddBiere : Screen("AddBiere")
    object TestApiScreen : Screen("TestApiScreen")
}