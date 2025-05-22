package com.example.yumifi1.features.recipe_details.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yumifi1.features.comment.ui.model.Comment
import com.example.yumifi1.R

@Composable
fun RecipeCommentsComponent(
    comments: List<Comment>,
    modifier: Modifier = Modifier,
    onItemClicked: (Comment?) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AdderComponent(
                labelRes = R.string.comment_add,
                modifier = Modifier.fillMaxWidth(),
            ) {
                onItemClicked(null)
            }
        }
        items(count = comments.size) { index ->
            Spacer(modifier = Modifier.height(16.dp))
            CommentItemComponent(
                comment = comments[index],
                modifier = Modifier.fillMaxWidth(),
            ) { item ->
                onItemClicked(item)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}