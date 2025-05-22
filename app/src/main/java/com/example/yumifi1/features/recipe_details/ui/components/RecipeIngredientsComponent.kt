package com.example.yumifi1.features.recipe_details.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yumifi1.R
import com.example.yumifi1.features.recipe_details.ui.model.Recipe

@Composable
fun RecipeIngredientsComponent(
    modifier: Modifier = Modifier,
    ingredients: List<Recipe.Ingredient>,
    onAddIngredientClicked: () -> Unit,
    onDeleteIngredientClicked: (Recipe.Ingredient) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(count = ingredients.size) { index ->
            IngredientItemComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                product = ingredients[index].product,
                onDeleteClicked = {
                    onDeleteIngredientClicked(ingredients[index])
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            AdderComponent(
                labelRes = R.string.recipe_add_ingredient,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClicked = onAddIngredientClicked,
            )
        }
    }
}