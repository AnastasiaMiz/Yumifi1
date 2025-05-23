package com.example.yumifi1.features.profile.ui

import com.example.yumifi1.features.comment.ui.model.Comment
import com.example.yumifi1.features.profile.ui.data.ProfileTab
import com.example.yumifi1.features.profile.ui.model.User
import com.example.yumifi1.features.recipe_details.ui.model.Recipe

data class ProfileState(
    val user: User = User(
        id = null,
        email = "",
    ),
    val recipes: List<Recipe> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val selectedTab: ProfileTab = ProfileTab.RECIPES,
)