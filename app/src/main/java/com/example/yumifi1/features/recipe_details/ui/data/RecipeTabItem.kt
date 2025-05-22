package com.example.yumifi1.features.recipe_details.ui.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class RecipeTabItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val type: RecipeDetailsTab,
)