package com.example.yumifi1.features.main.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.yumifi1.navigation.TabScreen

data class BottomNavItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val route: TabScreen,
)