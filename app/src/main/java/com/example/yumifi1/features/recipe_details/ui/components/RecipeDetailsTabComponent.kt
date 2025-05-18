package com.example.yumifi1.features.recipe_details.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailsTabComponent(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClicked)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontWeight = if (isSelected) {
                FontWeight.Medium
            } else {
                FontWeight.Normal
            },
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        )
    }
}