package com.example.yumifi1.features.profile.ui.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileTabItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val type: ProfileTab,
)