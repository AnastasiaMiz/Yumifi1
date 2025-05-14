package com.example.yumifi1.features.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.yumifi1.R
import com.example.yumifi1.features.splash.event.SplashEvent
import com.example.yumifi1.navigation.Screens

@Composable
fun SplashView(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        splashViewModel.event.collect { event ->
            val screen = when (event) {
                is SplashEvent.OpenAuthScreen -> Screens.Auth
                is SplashEvent.OpenMoviesScreen -> Screens.Recipes
            }
            navController.popBackStack(Screens.Splash.route, true)
            navController.navigate(screen.route)
        }
    }
    val context = LocalContext.current
    val appIcon = context.packageManager.getApplicationIcon(
        context.applicationInfo
    )

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 20.sp,
                )
            }
            LinearProgressIndicator()
        }
    }
}