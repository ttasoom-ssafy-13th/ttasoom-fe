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
import kotlinx.coroutines.launch

import javax.inject.Inject

private const val TAG = "BoardViewModel"

// 이 viewModel은 주로 게시글 확인할 때 및 댓글 관리 할 때 사용한다.
@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardByIdUseCase: GetBoardByIdUseCase,
    private val putBoardByIdUseCase: PutBoardByIdUseCase,
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

    private val _board = MutableLiveData<Board>()
    val board: LiveData<Board> get() = _board

    private val _commentsList = MutableLiveData<MutableList<Comment>>()
    val commentsList: LiveData<MutableList<Comment>> get() = _commentsList

    fun getBoardById() {
        viewModelScope.launch {
            getBoardByIdUseCase(post_id).onSuccess {
                _board.value = it
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    } //게시글 호출

    fun putBoardById(title: String, content: String) {
        viewModelScope.launch {
            putBoardByIdUseCase(post_id, title, content).onSuccess {
                _board.value = it
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    }//게시글 수정 --> 얘는 fragment안나간다

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
                _commentsList.value = ArrayList(it)
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 호출

    fun postComment(content: String) {
        viewModelScope.launch {
            val currentList = _commentsList.value
            val newList = currentList.toMutableList()

            postCommentUseCase(post_id, content).onSuccess {
                newList.add(it)
                _commentsList.value = newList
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 추가


    fun putComment(content_id: String, new_content: String) {
        val currentList = _commentsList.value
        val newList = currentList.toMutableList()

        viewModelScope.launch {
            putCommentUseCase(post_id, content_id, new_content).onSuccess {
                val index = newList.indexOfFirst { it.id == post_id }
                if (index != -1) {
                    newList[index] = newList[index].copy(content = new_content)
                    _commentsList.value = newList
                }
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    } // 댓글 변경

    fun deleteComment(content_id: String) {
        viewModelScope.launch {
            val currentList = _commentsList.value
            val newList = currentList.toMutableList()

            deleteCommentUseCase(post_id, content_id).onSuccess {
                val index = newList.indexOfFirst { it.id == post_id }
                if (index != -1) {
                    newList.removeAt(index)
                    _commentsList.value = newList
                }
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 삭제


}