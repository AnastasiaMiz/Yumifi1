package com.example.yumifi1.features.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yumifi1.features.comment.ui.model.Comment

@Composable
fun CommentItemComponent(
    comment: Comment,
    modifier: Modifier = Modifier,
    onClicked: (Comment) -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
            .clickable(
                onClick = { onClicked(comment) }
            )
            .padding(16.dp),
    ) {
        Text(
            text = comment.text,
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}