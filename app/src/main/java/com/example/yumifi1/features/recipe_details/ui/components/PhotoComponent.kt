package com.example.yumifi1.features.recipe_details.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.yumifi1.features.recipe_details.ui.model.RecipePhoto

@Composable
fun PhotoComponent(
    photo: RecipePhoto,
    onClicked: (RecipePhoto) -> Unit,
) {
    Image(
        painter = rememberAsyncImagePainter(photo.uri),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                onClick = { onClicked(photo) }
            ),
    )
}