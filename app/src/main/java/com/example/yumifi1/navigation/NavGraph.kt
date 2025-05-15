package com.example.yumifi1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.yumifi1.features.auth.ui.AuthView
import com.example.yumifi1.features.products.ui.ProductsView
import com.example.yumifi1.features.recipes.ui.RecipesView
import com.example.yumifi1.features.reg.ui.RegView
import com.example.yumifi1.features.splash.SplashView

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    /*
    Основной граф навигации
     */
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashView { nextView ->
                navController.popBackStack(Screen.Splash.route, true)
                navController.navigate(nextView.route)
            }
        }
        composable(route = Screen.Auth.route) {
            AuthView { nextView ->
                navController.popBackStack(Screen.Auth.route, true)
                navController.navigate(nextView.route)
            }
        }
        composable(route = Screen.Reg.route) {
            RegView { nextView ->
                navController.popBackStack(Screen.Reg.route, true)
                navController.navigate(nextView.route)
            }
        }
        /*
        Подграф навигации, нужен для навигации внутри табов
         */
        navigation(
            route = Screen.Home.route,
            startDestination = TabScreen.Recipes.route,
        ) {
            composable(route = TabScreen.Recipes.route) {
                RecipesView { nextView ->
                    navController.navigate(nextView.route) {
                        if (nextView == Screen.Auth) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            composable(route = TabScreen.Products.route) {
                ProductsView { nextView ->
                    navController.navigate(nextView.route) {
                        if (nextView == Screen.Auth) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}