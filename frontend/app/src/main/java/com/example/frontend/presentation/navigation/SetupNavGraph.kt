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
import com.example.frontend.presentation.feature.addbiere.AddBiere
import com.example.frontend.presentation.feature.listebiere.ListBiere
import com.example.frontend.presentation.feature.modifybiere.ModifyBiere
import com.example.frontend.presentation.feature.addbar.AddBarScreen
import com.example.frontend.presentation.feature.adddrink.AddDrinkScreen
import com.example.frontend.presentation.feature.deletebar.DeleteBarScreen
import com.example.frontend.presentation.feature.deletetypebiere.DeleteTypeBiereScreen
import com.example.frontend.presentation.feature.splashscreen.SplashScreenBeer
import com.example.frontend.presentation.feature.home.HomeScreen
import com.example.frontend.presentation.feature.editbarmenu.EditBarMenuScreen
import com.example.frontend.presentation.feature.modifybar.ModifyBarScreen
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
            route = Screen.ListBiere.route,
            arguments = listOf(navArgument("barId") { type = NavType.IntType })
        ) { backStackEntry ->

            val barId = backStackEntry.arguments?.getInt("barId") ?: -1

            ListBiere(
                barId = barId,
                navHostController = navHostController,
                modifier = modifier,
            )
        }
        composable(
            route = Screen.AddDrinkScreen.route,
            arguments = listOf(navArgument("barId") { type = NavType.IntType })
        ) { backStackEntry ->

            val barId = backStackEntry.arguments?.getInt("barId") ?: -1

            AddDrinkScreen(
                barId = barId,
                navHostController = navHostController,
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
            arguments = listOf(
                navArgument("drinkId") { type = NavType.IntType },
                navArgument("barId") { type = NavType.IntType },
                navArgument("volume") { type = NavType.FloatType },
                navArgument("price") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val drinkId = backStackEntry.arguments?.getInt("drinkId") ?: -1
            val barId = backStackEntry.arguments?.getInt("barId") ?: -1
            val volume = backStackEntry.arguments?.getFloat("volume") ?: 0f
            val price = backStackEntry.arguments?.getFloat("price")?:0f

            ModifyBiere(
                navHostController = navHostController,
                modifier = modifier,
                drinkId = drinkId,
                barId = barId,
                volume=volume,
                price= price,
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

        composable(
            route = Screen.DeleteBarScreen.route
        ) {
            DeleteBarScreen(
                navHostController = navHostController
            )
        }

        composable(
            route = Screen.ModifyBar.route,
            arguments = listOf(navArgument("barId") { type = NavType.IntType })
        ) { backStackEntry ->

            val barId = backStackEntry.arguments?.getInt("barId") ?: -1
            ModifyBarScreen(
                navHostController = navHostController,
                barId = barId
            )
        }

        composable(
            route = Screen.DeleteTypeBiereScreen.route
        ) {
            DeleteTypeBiereScreen(
                navHostController = navHostController
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
    object ListBiere : Screen("ListBiere/{barId}") {
        fun createRoute(barId: Int) = "ListBiere/$barId"
    }

    object AddBiere : Screen("AddBiere")
    object ModifyBiere : Screen("modifyBiere/{drinkId}/{barId}/{volume}/{price}") {
        fun createRoute(drinkId: Int, barId: Int, volume: Double,price: Double): String {
            return "modifyBiere/$drinkId/$barId/$volume/$price"
        }
    }

    object AddBarScreen : Screen("AddBarScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object EditBarMenuScreen : Screen("EditBarMenuScreen")
    object DeleteBarScreen : Screen("DeleteBarScreen")
    object AddDrinkScreen : Screen("AddDrinkScreen/{barId}") {
        fun createRoute(barId: Int) = "AddDrinkScreen/$barId"
    }
    object ModifyBar : Screen("ModifyBar/{barId}") {
        fun createRoute(barId: Int) = "ModifyBar/$barId"
    }
    object DeleteTypeBiereScreen : Screen("DeleteTypeBiereScreen")
}