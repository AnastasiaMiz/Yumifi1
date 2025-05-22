package com.example.yumifi1.features.recipe_details.ui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.yumifi1.R

object RecipeTabsItems {
    val items = listOf(
        RecipeTabItem(
            labelRes = R.string.recipe_description_tab_title,
            icon = Icons.Default.Menu,
            type = RecipeDetailsTab.DESCRIPTION,
        ),
        RecipeTabItem(
            labelRes = R.string.recipe_ingredients_tab_title,
            icon = Icons.Default.ShoppingCart,
            type = RecipeDetailsTab.INGREDIENTS,
        ),
        RecipeTabItem(
            labelRes = R.string.recipe_comments_tab_title,
            icon = Icons.Default.Edit,
            type = RecipeDetailsTab.COMMENTS,
        )
    )
}