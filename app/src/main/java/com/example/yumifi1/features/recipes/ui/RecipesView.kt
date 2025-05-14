package com.example.yumifi1.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yumifi1.features.recipes.ui.event.RecipesEvent
import com.example.yumifi1.navigation.Screens

@Composable
fun RecipesView(
    navController: NavController,
    recipesViewModel: RecipesViewModel = hiltViewModel(),
) {
    val state by recipesViewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        recipesViewModel.event.collect { event ->
            when(event) {
                is RecipesEvent.OpenAuthView -> {
                    navController.popBackStack()
                    navController.navigate(Screens.Auth.route)
                }
            }
        }
    }

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            ToolbarComponent(
                onSearchClicked = {},
                onLogoutClicked = recipesViewModel::onLogoutClicked
            )
        }
    }
}

@Composable
private fun ToolbarComponent(
    onSearchClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        IconButton(
            onClick = onSearchClicked
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
        IconButton(
            onClick = onLogoutClicked
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
    }
}
