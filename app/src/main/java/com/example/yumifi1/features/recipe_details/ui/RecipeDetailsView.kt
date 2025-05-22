package com.example.yumifi1.features.recipe_details.ui

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import com.example.yumifi1.features.recipe_details.ui.components.RecipeCommentsComponent
import com.example.yumifi1.features.recipe_details.ui.components.RecipeDescriptionComponent
import com.example.yumifi1.features.recipe_details.ui.components.RecipeIngredientsComponent
import com.example.yumifi1.features.recipe_details.ui.data.RecipeDetailsTab
import com.example.yumifi1.features.recipe_details.ui.data.RecipeTabsItems
import com.example.yumifi1.features.recipe_details.ui.event.RecipeDetailsEvent
import com.example.yumifi1.navigation.Screen

@Composable
fun RecipeDetailsView(
    viewModel: RecipeDetailsViewModel = hiltViewModel(),
    back: () -> Unit,
    openNextView: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is RecipeDetailsEvent.OnBackNavigate -> { back() }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        ToolbarComponent(
            state = state,
            back = back,
            onDeleteClicked = {
                viewModel.onDeleteClicked()
                back()
            }
        )
        ContentComponent(
            state = state,
            viewModel = viewModel,
            openNextView = openNextView,
        )
    }
}

@Composable
private fun ToolbarComponent(
    state: RecipeDetailsState,
    back: () -> Unit,
    onDeleteClicked: () -> Unit,
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
            onClick = { back() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(id = R.string.recipe_details_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        IconButton(
            enabled = state.isRecipeOwnedUser,
            onClick = { onDeleteClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = if (state.recipe.id == null || !state.isRecipeOwnedUser) {
                    Color.LightGray
                } else {
                    MaterialTheme.colorScheme.primary
                },
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ContentComponent(
    state: RecipeDetailsState,
    viewModel: RecipeDetailsViewModel,
    openNextView: (Screen) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
    ) {
        OutlinedTextField(
            value = state.recipe.name,
            label = {
                Text(text = stringResource(id = R.string.recipe_name_label))
            },
            onValueChange = viewModel::onNameChanged,
            readOnly = !state.isRecipeOwnedUser || state.isLoading,
            maxLines = 2,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            windowInsets = WindowInsets(
                left = 0,
                right = 0,
                top = 0,
                bottom = 0,
            ),
        ) {
            val items = RecipeTabsItems.items.filter { tab ->
                if (state.recipe.id == null) {
                    tab.type != RecipeDetailsTab.COMMENTS
                } else {
                    true
                }
            }
            items.forEach { item ->
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
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            when(state.selectedTab) {
                RecipeDetailsTab.DESCRIPTION -> {
                    RecipeDescriptionComponent(
                        description = state.recipe.description,
                        isEnabled = state.isRecipeOwnedUser && !state.isLoading,
                        onTextChanged = viewModel::onDescriptionChanged,
                        modifier = Modifier.padding(top = 16.dp),
                    )
                }
                RecipeDetailsTab.INGREDIENTS -> {
                    RecipeIngredientsComponent(
                        modifier = Modifier.fillMaxWidth(),
                        ingredients = state.recipe.ingredients,
                        isEditable = state.isRecipeOwnedUser && !state.isLoading,
                        onAddIngredientClicked = {
                            openNextView(Screen.AddIngredient)
                        },
                        onDeleteIngredientClicked = viewModel::onDeleteIngredientClicked,
                    )
                }
                RecipeDetailsTab.COMMENTS -> {
                    RecipeCommentsComponent(
                        comments = state.recipe.comments,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        onItemClicked = { comment ->
                            state.recipe.id?.let { recipeId ->
                                openNextView(
                                    Screen.Comment(
                                        recipeId = recipeId,
                                        commentId = comment?.id
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
        if (state.recipe.id == null || state.isRecipeOwnedUser) {
            Button(
                onClick = viewModel::onSaveClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = !state.isLoading,
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}