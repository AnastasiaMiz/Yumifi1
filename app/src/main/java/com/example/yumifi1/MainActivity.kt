package com.example.yumifi1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yumifi1.event.MainEvent
import com.example.yumifi1.features.main.ui.components.BottomNavigationBar
import com.example.yumifi1.navigation.NavGraph
import com.example.yumifi1.navigation.Screen
import com.example.yumifi1.navigation.TabScreen
import com.example.yumifi1.navigation.TabScreen.Companion.isTabRoute
import com.example.yumifi1.ui.theme.Yumifi1Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yumifi1Theme {
                App()
            }
        }
    }
}

@Composable
private fun App(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Splash.route

    LaunchedEffect(Unit) {
        viewModel.event
            .collect { event ->
                when(event) {
                    is MainEvent.ShowMessage -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                        )
                    }
                }
            }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute.isTabRoute(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                BottomNavigationBar(navController)
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        NavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
        )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Yumifi1Theme {
        Greeting("Android")
    }
}