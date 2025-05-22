package com.example.yumifi1.features.comment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yumifi1.R
import androidx.compose.runtime.getValue
import com.example.yumifi1.navigation.Screen

@Composable
fun CommentView(
    viewModel: CommentViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    openNextView: (Screen?) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ToolbarComponent(
            state = state,
            back = { openNextView(null) },
            onDeleteClicked = {
                viewModel.onDeleteClicked()
                openNextView(null)
            }
        )
        ContentComponent(
            viewModel = viewModel,
            modifier = modifier
        ) { nextView ->
            openNextView(nextView)
        }
    }
}

@Composable
private fun ToolbarComponent(
    state: CommentState,
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
            text = stringResource(id = R.string.comment_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        IconButton(
            enabled = state.comment.id != null,
            onClick = { onDeleteClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = if (state.comment.id == null) {
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
    viewModel: CommentViewModel,
    modifier: Modifier = Modifier,
    openNextView: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {

    }
}