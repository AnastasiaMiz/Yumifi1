package com.example.yumifi1.features.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yumifi1.features.recipe_details.ui.model.Recipe
import com.example.yumifi1.features.recipes.ui.components.AddRecipeItemComponent
import com.example.yumifi1.features.recipes.ui.components.RecipeItemComponent

@Composable
fun RecipesComponent(
    recipes: List<Recipe>,
    onItemClicked: (Long?) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
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