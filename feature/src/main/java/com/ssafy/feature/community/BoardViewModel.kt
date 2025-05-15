package com.ssafy.feature.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.usecase.comment.DeleteCommentUseCase
import com.ssafy.domain.community.usecase.comment.GetCommentUseCase
import com.ssafy.domain.community.usecase.comment.PostCommentUseCase
import com.ssafy.domain.community.usecase.comment.PutCommentUseCase
import com.ssafy.domain.community.usecase.community.DeleteBoardByIdUseCase
import com.ssafy.domain.community.usecase.community.GetBoardByIdUseCase
import com.ssafy.domain.community.usecase.community.PostBoardLikeUseCase
import com.ssafy.domain.community.usecase.community.PutBoardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

private const val TAG = "BoardViewModel"

// 이 viewModel은 주로 게시글 확인할 때 및 댓글 관리 할 때 사용한다.
@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardByIdUseCase: GetBoardByIdUseCase,
    private val deleteBoardByIdUseCase: DeleteBoardByIdUseCase,
    private val getCommentUseCase: GetCommentUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val putCommentUseCase: PutCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val postBoardLikeUseCase: PostBoardLikeUseCase
) : ViewModel() {

    var post_id: String = ""
        get() = field
        set(value) {
            field = value
        }

    private val _board = MutableStateFlow(Board())
    val board: StateFlow<Board> get() = _board

    private val _commentsList = MutableStateFlow(mutableListOf<Comment>())
    val commentsList: StateFlow<MutableList<Comment>> get() = _commentsList

//    fun updateBoard(title : String, content : String){
//        _board.value.title = title
//        _board.value.content=content
//    }

    fun getBoardById() {
        viewModelScope.launch {
            getBoardByIdUseCase(post_id).onSuccess {
                _board.value = it
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    } //게시글 호출


    fun deleteBoardById() {
        viewModelScope.launch {
            deleteBoardByIdUseCase(post_id).onSuccess {
                Log.d(TAG, "deleteBoardById: 삭제 완료")
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//게시글 삭제

    fun getComments() {
        viewModelScope.launch {
            getCommentUseCase(post_id).onSuccess {
                _commentsList.value =it
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 호출

    fun postComment(content: String) {
        viewModelScope.launch {
            postCommentUseCase(post_id, content).onSuccess {newComment ->
                _commentsList.update { currentList ->
                    (currentList + newComment).toMutableList()
                }
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 추가


    fun putComment(content_id: String, new_content: String) {
        val currentList = _commentsList.value

        viewModelScope.launch {
            putCommentUseCase(post_id, content_id, new_content).onSuccess {
                val index = currentList.indexOfFirst { it.id == content_id }
                if (index != -1) {
                    _commentsList.value.get(index).content=new_content
                }
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    } // 댓글 변경

    fun deleteComment(comment_id: String) {
        viewModelScope.launch {
            val currentList = _commentsList.value

            deleteCommentUseCase(post_id, comment_id).onSuccess {
                val index = currentList.indexOfFirst { it.id == comment_id }
                if (index != -1) {
                    _commentsList.value.removeAt(index)
                }
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 삭제

    fun postLikeEmoji(){
        viewModelScope.launch {
            postBoardLikeUseCase(post_id).onSuccess {
                Log.d(TAG, "postLikeEmoji: success")
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    } //하트추가


}