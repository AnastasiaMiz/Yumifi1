package com.example.yumifi1.features.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import androidx.compose.runtime.getValue
import com.example.yumifi1.features.recipes.ui.components.RecipeItemComponent
import com.example.yumifi1.features.search.ui.components.SelectableProductComponent
import com.example.yumifi1.navigation.Screen

@Composable
fun SearchView(
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    openNextView: (Screen?) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ToolbarComponent(back = { openNextView(null) })
        ContentComponent(
            viewModel = viewModel,
            openNextView = { nextView ->
                openNextView(nextView)
            }
        )
    }
}

@Composable
private fun ToolbarComponent(
    back: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
            text = stringResource(id = R.string.search_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(
                alignment = Alignment.Center,
            )
        )
    }
}

@Composable
private fun ContentComponent(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
    openNextView: (Screen) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.recipes.isNotEmpty()) {
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (state.recipes.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
                items(
                    count = state.recipes.size
                ) { index ->
                    RecipeItemComponent(
                        recipe = state.recipes[index],
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(end = 16.dp),
                        onItemClicked = {
                            openNextView(Screen.RecipeDetails(
                                recipeId = state.recipes[index].id
                            ))
                        }
                    )
                }
            }
            HorizontalDivider()
        }
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(
                count = state.products.size
            ) { index ->
                SelectableProductComponent(
                    selectableData = state.products[index],
                    modifier = modifier.fillMaxWidth(),
                ) { item ->
                    viewModel.onSelectProduct(item)
                }
            }
        }
    }
}