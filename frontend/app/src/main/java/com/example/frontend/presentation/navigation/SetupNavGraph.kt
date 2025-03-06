package com.example.frontend.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.frontend.presentation.feature.bar.PageBar
import com.example.frontend.presentation.feature.biere.AddBiere
import com.example.frontend.presentation.feature.biere.ListBiere
import com.example.frontend.presentation.feature.biere.ModifyBiere
import com.example.frontend.presentation.feature.addbar.AddBarScreen
import com.example.frontend.presentation.feature.splashscreen.SplashScreenBeer
import com.example.frontend.presentation.feature.home.HomeScreen
import com.example.frontend.presentation.feature.editbarmenu.EditBarMenuScreen
import com.example.frontend.presentation.feature.settings.SettingsScreen

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SetupNavGraph(
    modifier: Modifier,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.SplachScreenBeer.route
        ) {
            SplashScreenBeer(
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.PageBar.route,
            arguments = listOf(navArgument("barId") { type = NavType.IntType })
        ) { backStackEntry ->

            val barId = backStackEntry.arguments?.getInt("barId") ?: -1

            PageBar(
                modifier = modifier,
                navHostController = navHostController,
                barId = barId
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
        composable(
            route = Screen.ModifyBiere.route,
            arguments = listOf(navArgument("drinkId") { type = NavType.IntType })
        ) { backStackEntry ->
            val drinkId = backStackEntry.arguments?.getInt("drinkId") ?: -1

            ModifyBiere(
                navHostController = navHostController,
                modifier = modifier,
                drinkId = drinkId
            )
        }
        composable(
            route = Screen.AddBarScreen.route
        ) {
            AddBarScreen(
                navHostController = navHostController,
            )
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(
                navHostController = navHostController,
            )
        }
        composable(
            route = Screen.EditBarMenuScreen.route
        ) {
            EditBarMenuScreen(
                navHostController = navHostController,
            )
        }
    }
}

sealed class Screen(val route: String) {
    object SplachScreenBeer : Screen("SplashScreenBeer")
    object PageBar : Screen("PageBar/{barId}") {
        fun createRoute(barId: Int) = "PageBar/$barId"
    }
    object HomeScreen : Screen("HomeScreen")
    object ListBiere : Screen("ListBiere")
    object AddBiere : Screen("AddBiere")
    object ModifyBiere : Screen("ModifyBiere/{drinkId}") {
        fun createRoute(drinkId: Int) = "ModifyBiere/$drinkId"
    }
    object TestApiScreen : Screen("TestApiScreen")
    object AddBarScreen : Screen("AddBarScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object EditBarMenuScreen : Screen("EditBarMenuScreen")
}