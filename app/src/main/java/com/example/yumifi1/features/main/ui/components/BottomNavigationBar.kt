package com.example.yumifi1.features.main.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.yumifi1.features.main.ui.model.BottomNavItems

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItems.items.forEach { navItem ->
            val label = stringResource(id = navItem.labelRes)
            NavigationBarItem(
                selected = currentRoute == navItem.route.toString(),
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = label,
                    )
                },
                label = {
                    Text(text = label)
                }
            )
        }
    }
}