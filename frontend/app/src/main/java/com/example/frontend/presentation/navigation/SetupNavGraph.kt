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
import com.example.frontend.data.vo.DrinkVo
import com.example.frontend.presentation.splashscreen.SplashScreenBeer
import com.example.frontend.presentation.bar.PageBar
import com.example.frontend.presentation.biere.AddBiere
import com.example.frontend.presentation.biere.ListBiere
import com.example.frontend.presentation.biere.ModifyBiere
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
    }
}

sealed class Screen(val route: String) {
    object SplachScreenBeer : Screen("SplashScreenBeer")
    object PageBar : Screen("PageBar")
    object HomeScreen : Screen("HomeScreen")
    object ListBiere : Screen("ListBiere")
    object AddBiere : Screen("AddBiere")
    object ModifyBiere : Screen("ModifyBiere/{drinkId}") {
        fun createRoute(drinkId: Int) = "ModifyBiere/$drinkId"
    }
}