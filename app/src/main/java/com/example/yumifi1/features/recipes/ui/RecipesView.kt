package com.example.yumifi1.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yumifi1.features.recipes.ui.components.AddRecipeItemComponent
import com.example.yumifi1.features.recipes.ui.components.RecipeItemComponent
import com.example.yumifi1.features.recipes.ui.event.RecipesEvent
import com.example.yumifi1.features.recipes.ui.model.Recipe
import com.example.yumifi1.navigation.Screens
import com.example.yumifi1.R

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
            RecipesContentComponent(
                recipes = state.recipes,
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
        Text(
            text = stringResource(id = R.string.recipes_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
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

@Composable
private fun RecipesContentComponent(
    recipes: List<Recipe>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = recipes.size + 1
        ) { index ->
            if (index == 0) {
                AddRecipeItemComponent {
                    // TODO: открыть экран создания рецепта
                }
            } else {
                recipes.getOrNull(index)?.let { recipe ->
                    RecipeItemComponent(
                        recipe = recipe
                    )
                }
            }
        }
    }
}
