package com.example.yumifi1.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import com.example.yumifi1.features.recipe_details.ui.model.Recipe
import com.example.yumifi1.features.recipes.ui.components.AddRecipeItemComponent
import com.example.yumifi1.features.recipes.ui.components.RecipeItemComponent
import com.example.yumifi1.navigation.Screen

@Composable
fun RecipesView(
    recipesViewModel: RecipesViewModel = hiltViewModel(),
    openView: (Screen) -> Unit,
) {
    val state by recipesViewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ToolbarComponent(
            onSearchClicked = { openView(Screen.Search) },
            onProfileClicked = { openView(Screen.Profile) }
        )
        RecipesContentComponent(
            recipes = state.recipes,
            onItemClicked = { recipeId ->
                openView(Screen.RecipeDetails(recipeId))
            }
        )
    }
}

@Composable
private fun ToolbarComponent(
    onSearchClicked: () -> Unit,
    onProfileClicked: () -> Unit
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
            text = stringResource(id = R.string.recipes),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        IconButton(
            onClick = onProfileClicked
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun RecipesContentComponent(
    recipes: List<Recipe>,
    onItemClicked: (Long?) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
    ) {
        item {
            AddRecipeItemComponent {
                onItemClicked(null)
            }
        }
        items(count = recipes.size) { index ->
            RecipeItemComponent(
                recipe = recipes[index],
                onItemClicked = { onItemClicked(recipes[index].id) }
            )
        }
    }
}
