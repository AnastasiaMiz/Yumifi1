package com.example.yumifi1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.yumifi1.features.auth.ui.AuthView
import com.example.yumifi1.features.product_details.ui.ProductDetailsView
import com.example.yumifi1.features.product_details.ui.ProductDetailsViewModel
import com.example.yumifi1.features.products.ui.ProductsView
import com.example.yumifi1.features.recipe_details.ui.RecipeDetailsView
import com.example.yumifi1.features.recipe_details.ui.RecipeDetailsViewModel
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
                navController.navigate(nextView.route) {
                    launchSingleTop = true
                }
            }
        }
        composable(route = Screen.Auth.route) {
            AuthView { nextView ->
                navController.popBackStack(Screen.Auth.route, true)
                navController.navigate(nextView.route) {
                    launchSingleTop = true
                }
            }
        }
        composable(route = Screen.Reg.route) {
            RegView { nextView ->
                navController.popBackStack(Screen.Reg.route, true)
                navController.navigate(nextView.route) {
                    launchSingleTop = true
                }
            }
        }
        composable(
            route = Screen.ProductDetails.getNavigationRoute(),
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            ),
        ) { backStackEntry ->
            val viewModel: ProductDetailsViewModel = hiltViewModel(backStackEntry)
            ProductDetailsView(viewModel = viewModel) {
                navController.popBackStack()
            }
        }
        composable(
            route = Screen.RecipeDetails.getNavigationRoute(),
            arguments = listOf(
                navArgument("recipeId") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            ),
        ) { backStackEntry ->
            val viewModel: RecipeDetailsViewModel = hiltViewModel(backStackEntry)
            RecipeDetailsView(viewModel = viewModel) {
                navController.popBackStack()
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
                        launchSingleTop = true
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
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}