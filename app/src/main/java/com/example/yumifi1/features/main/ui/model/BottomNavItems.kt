package com.example.yumifi1.features.main.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import com.example.yumifi1.R
import com.example.yumifi1.navigation.Screen
import com.example.yumifi1.navigation.TabScreen

object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            labelRes = R.string.recipes,
            icon = Icons.AutoMirrored.Filled.List,
            route = TabScreen.Recipes,
        )
    )
}