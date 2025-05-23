package com.example.yumifi1.features.profile.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yumifi1.features.comment.ui.model.Comment

@Composable
fun CommentsComponent(
    comments: List<Comment>,
    onItemClicked: (Comment) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(count = comments.size) { index ->
            Spacer(modifier = Modifier.height(16.dp))
            CommentItemComponent(
                comment = comments[index],
                modifier = Modifier.fillMaxWidth(),
            ) { item ->
                onItemClicked(item)
            }
        }
    }
}