package com.example.yumifi1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.yumifi1.features.auth.ui.AuthView
import com.example.yumifi1.features.main.ui.MainView
import com.example.yumifi1.features.recipes.ui.RecipesView
import com.example.yumifi1.features.reg.ui.RegView
import com.example.yumifi1.features.splash.SplashView

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    setBottomBarVisible: (Boolean) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash,
    ) {
        composable<Screen.Splash> {
            SplashView { nextView ->
                navController.popBackStack(Screen.Splash, true)
                navController.navigate(nextView)
            }
            setBottomBarVisible(false)
        }
        composable<Screen.Auth> {
            AuthView { nextView ->
                navController.popBackStack(Screen.Auth, true)
                navController.navigate(nextView)
            }
            setBottomBarVisible(false)
        }
        composable<Screen.Reg> {
            RegView { nextView ->
                navController.popBackStack(Screen.Reg, true)
                navController.navigate(nextView)
            }
            setBottomBarVisible(false)
        }
        navigation<Screen.Home>(
            startDestination = TabScreen.Recipes,
        ) {
            composable<TabScreen.Recipes> {
                RecipesView { nextView ->
                    if (nextView == Screen.Auth) {
                        navController.navigate(nextView) {
                            popUpTo(Screen.Home) {
                                inclusive = true
                            }
                        }
                    }
                }
                setBottomBarVisible(true)
            }
        }
    }
}