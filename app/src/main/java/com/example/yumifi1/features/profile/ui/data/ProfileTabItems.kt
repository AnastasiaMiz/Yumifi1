package com.example.yumifi1.features.profile.ui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import com.example.yumifi1.R

object ProfileTabItems {
    val items = listOf(
        ProfileTabItem(
            labelRes = R.string.profile_recipes,
            icon = Icons.AutoMirrored.Filled.List,
            type = ProfileTab.RECIPES,
        ),
        ProfileTabItem(
            labelRes = R.string.profile_comments,
            icon = Icons.Default.Edit,
            type = ProfileTab.COMMENTS,
        )
    )
}