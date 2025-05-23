package com.example.yumifi1.features.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import com.example.yumifi1.features.profile.ui.event.ProfileEvent
import com.example.yumifi1.navigation.Screen
import androidx.compose.runtime.getValue
import com.example.yumifi1.features.profile.ui.components.CommentsComponent
import com.example.yumifi1.features.profile.ui.components.RecipesComponent
import com.example.yumifi1.features.profile.ui.data.ProfileTab
import com.example.yumifi1.features.profile.ui.data.ProfileTabItems
import com.example.yumifi1.features.recipe_details.ui.data.RecipeDetailsTab
import com.example.yumifi1.features.recipe_details.ui.data.RecipeTabsItems

@Composable
fun ProfileView(
    viewModel: ProfileViewModel = hiltViewModel(),
    openNextView: (Screen?) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is ProfileEvent.OpenAuthView -> {
                    openNextView(Screen.Auth)
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ToolbarComponent(
            onBackClicked = { openNextView(null) },
            onLogoutClicked = {
                viewModel.onLogoutClicked()
            }
        )
        ContentComponent(
            viewModel = viewModel,
        ) { nextView ->
            openNextView(nextView)
        }
    }
}

@Composable
private fun ToolbarComponent(
    onBackClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        IconButton(
            onClick = onBackClicked
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(id = R.string.profile_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        IconButton(
            onClick = onLogoutClicked
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ExitToApp,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ContentComponent(
    viewModel: ProfileViewModel,
    openNextView: (Screen?) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = state.user.email,
            onValueChange = viewModel::onEmailChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            windowInsets = WindowInsets(
                left = 0,
                right = 0,
                top = 0,
                bottom = 0,
            ),
        ) {
            ProfileTabItems.items.forEach { item ->
                val label = stringResource(item.labelRes)
                NavigationBarItem(
                    selected = state.selectedTab == item.type,
                    onClick = { viewModel.selectTab(item.type) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = label,
                        )
                    },
                    label = {
                        Text(text = label)
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when(state.selectedTab) {
                ProfileTab.RECIPES -> {
                    RecipesComponent(
                        recipes = state.recipes,
                    ) { recipeId ->
                        openNextView(
                            Screen.RecipeDetails(recipeId = recipeId)
                        )
                    }
                }
                ProfileTab.COMMENTS -> {
                    CommentsComponent(
                        comments = state.comments,
                    ) { comment ->
                        val recipe = state.recipes.find { recipe ->
                            recipe.comments.any { it.id == comment.id }
                        }
                        recipe?.id?.let { recipeId ->
                            openNextView(
                                Screen.Comment(
                                    commentId = comment.id,
                                    recipeId = recipeId,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}