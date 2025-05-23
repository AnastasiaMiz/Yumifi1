package com.example.yumifi1.features.profile.ui.model

import com.example.yumifi1.features.comment.ui.model.Comment
import com.example.yumifi1.features.recipe_details.ui.model.Recipe

data class UserWithContent(
    val user: User,
    val recipes: List<Recipe>,
    val comments: List<Comment>,
)