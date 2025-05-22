package com.example.yumifi1.features.comment.ui

import androidx.lifecycle.ViewModel
import com.example.yumifi1.features.comment.interactor.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CommentState())
    val state: StateFlow<CommentState> = _state.asStateFlow()

    fun onDeleteClicked() {
        // TODO: удалить комментарий
    }
}