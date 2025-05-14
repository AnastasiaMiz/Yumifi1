package com.example.yumifi1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yumifi1.features.auth.ui.AuthView
import com.example.yumifi1.features.reg.ui.RegView
import com.example.yumifi1.features.splash.SplashView

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
    ) {
        composable(route = Screens.Splash.route) {
            SplashView(navController = navController)
        }
        composable(route = Screens.Auth.route) {
            AuthView(navController = navController)
        }
        composable(route = Screens.Reg.route) {
            RegView(navController = navController)
        }
    }
}