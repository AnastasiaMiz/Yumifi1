package com.example.yumifi1.features.recipe_details.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yumifi1.features.recipe_details.ui.model.RecipePhoto

@Composable
fun RecipePhotosComponent(
    modifier: Modifier,
    isMy: Boolean,
    photos: List<RecipePhoto>,
    onPhotoSelected: (List<Uri>) -> Unit,
    onPhotoClicked: (RecipePhoto) -> Unit,
) {
    LazyRow(
        modifier = modifier,
    ) {
        if (isMy) {
            item {
                Spacer(modifier = Modifier.width(16.dp))
                AddPhotoComponent(
                    onPhotoSelected = onPhotoSelected,
                )
            }
        }
        items(count = photos.size) { index ->
            Spacer(modifier = Modifier.width(16.dp))
            PhotoComponent(
                photo = photos[index],
                isMy = isMy,
                onClicked = onPhotoClicked,
            )
        }
    }
}