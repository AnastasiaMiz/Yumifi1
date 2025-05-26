package com.example.yumifi1.features.comment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.yumifi1.features.comment.ui.event.CommentEvent
import com.example.yumifi1.navigation.Screen

@Composable
fun CommentView(
    viewModel: CommentViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    openNextView: (Screen?) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is CommentEvent.OnBackNavigate -> {
                    openNextView(null)
                }
            }
        }
    }

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
            modifier = modifier.padding(16.dp)
        )
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
            enabled = state.isCommentOwnedUser,
            onClick = { onDeleteClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = if (state.comment.id == null || !state.isCommentOwnedUser) {
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
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            value = state.comment.text,
            onValueChange = viewModel::onTextChanged,
            readOnly = !state.isCommentOwnedUser || state.isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        if (state.comment.id == null || state.isCommentOwnedUser) {
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = !state.isLoading,
                onClick = viewModel::onSaveClicked,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}