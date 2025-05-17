package com.example.yumifi1.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import com.example.yumifi1.features.splash.event.SplashEvent
import com.example.yumifi1.navigation.Screen
import com.example.yumifi1.navigation.TabScreen

@Composable
fun SplashView(
    splashViewModel: SplashViewModel = hiltViewModel(),
    openNextView: (Screen) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        splashViewModel.event.collect { event ->
            val screen = when (event) {
                is SplashEvent.OpenAuthScreen -> Screen.Auth
                is SplashEvent.OpenMoviesScreen -> TabScreen.Recipes
            }
            openNextView(screen)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.logo_yumifi),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .clip(CircleShape),
        )
        LinearProgressIndicator()
    }
}