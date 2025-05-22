package com.example.yumifi1.features.comment.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yumifi1.features.auth.interactor.AuthRepository
import com.example.yumifi1.features.comment.interactor.CommentRepository
import com.example.yumifi1.features.comment.ui.event.CommentEvent
import com.example.yumifi1.message.MessageHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val commentRepository: CommentRepository,
    private val authRepository: AuthRepository,
    private val messageHandler: MessageHandler,
) : ViewModel() {

    private val _state = MutableStateFlow(CommentState())
    val state: StateFlow<CommentState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<CommentEvent>()
    val event: SharedFlow<CommentEvent> = _event.asSharedFlow()

    init {
        val commentId = savedStateHandle.get<Long>("commentId")?.takeIf { it != -1L }
        val recipeId = savedStateHandle.get<Long>("recipeId")?.takeIf { it != -1L }
        if (recipeId == null) {
            viewModelScope.launch {
                delay(100L)
                _event.emit(CommentEvent.OnBackNavigate)
            }
        } else {
            viewModelScope.launch {
                if (commentId != null) {
                    commentRepository.getComment(commentId)
                        .onSuccess { comment ->
                            val userId = authRepository.getCurrentUserId()
                            val isCommentOwnedUser = commentRepository.isCommentOwnedUser(
                                commentId = commentId,
                                userId = userId,
                            )
                            _state.update { state ->
                                state.copy(
                                    recipeId = recipeId,
                                    comment = comment,
                                    isCommentOwnedUser = isCommentOwnedUser,
                                )
                            }
                        }
                        .onFailure { error ->
                            messageHandler.sendMessage("Ошибка: ${error.message}")
                            _event.emit(CommentEvent.OnBackNavigate)
                        }
                } else {
                    _state.update { state ->
                        state.copy(
                            recipeId = recipeId,
                            isCommentOwnedUser = true,
                        )
                    }
                }
            }
        }
    }

    fun onDeleteClicked() {
        val commentId = state.value.comment.id
        viewModelScope.launch {
            if (commentId == null) {
                messageHandler.sendMessage("Нельзя удалить несуществующий комментарий")
            } else {
                commentRepository.deleteCommentById(commentId)
            }
        }
    }

    fun onSaveClicked() {
        val recipeId = state.value.recipeId ?: return
        viewModelScope.launch {
            val comment = state.value.comment
            if (comment.id == null) {
                commentRepository.insertComment(
                    recipeId = recipeId,
                    comment = comment,
                )
            } else {
                commentRepository.updateComment(comment)
            }
            _event.emit(CommentEvent.OnBackNavigate)
        }
    }

    fun onTextChanged(text: String) {
        _state.update { state ->
            state.copy(
                comment = state.comment.copy(
                    text = text,
                )
            )
        }
    }
}