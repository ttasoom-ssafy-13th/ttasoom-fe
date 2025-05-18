package com.ssafy.feature.community

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.ssafy.feature.LikedSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

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
    private val postBoardLikeUseCase: PostBoardLikeUseCase,
) : ViewModel() {

    var post_id: String = ""
        get() = field
        set(value) {
            field = value
        } // 게시글 ID 따오기

    private val _board = MutableStateFlow(Board())
    val board: StateFlow<Board> = _board // 게시글 정보

    private val _commentsList = MutableStateFlow(mutableListOf<Comment>())
    val commentsList: StateFlow<MutableList<Comment>> get() = _commentsList //댓글 정보

    private val _likeCnt = MutableStateFlow(0)
    val likeCnt : StateFlow<Int> get()=_likeCnt // 좋아요 수

    private val _commentCnt= MutableStateFlow(0)
    val commentCnt :  StateFlow<Int> get()=_commentCnt

    private val _likedFlag = MutableStateFlow(false)
    val likedFlag : StateFlow<Boolean> get() =_likedFlag



    fun getBoardById() {
        viewModelScope.launch {
            getBoardByIdUseCase(post_id).onSuccess {
                _board.value = it.copy()
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
                _commentsList.value = ArrayList(it)
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 호출

    fun postComment(content: String) {
        viewModelScope.launch {

            postCommentUseCase(post_id, content).onSuccess {newComment->
                val updatedList = _commentsList.value.toMutableList().apply {
                    add(newComment)
                }

                _commentsList.value = updatedList
                _commentCnt.value = updatedList.size
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }

        }
    }//댓글 추가


    fun putComment(comment_id: String, new_content: String) {

        viewModelScope.launch {
            putCommentUseCase(post_id, comment_id, new_content).onSuccess {

                _commentsList.value = _commentsList.value.map {
                    if (it.id == comment_id) it.copy(content = new_content) else it
                }.toMutableList()

            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    } // 댓글 변경

    fun deleteComment(comment_id: String) {

        viewModelScope.launch {
            deleteCommentUseCase(post_id, comment_id).onSuccess {

                _commentsList.value = _commentsList.value.filterNot { it.id == comment_id }.toMutableList()
                _commentCnt.value=_commentsList.value.size
            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    }//댓글 삭제

    fun postLikeEmoji() {


        viewModelScope.launch {
            postBoardLikeUseCase(post_id).onSuccess {
                Log.d(TAG, "postLikeEmoji: ${it}")
                Log.d(TAG, "postLikeEmoji: ${_board.value}")
                _likedFlag.value=if(it.liked_users.contains("sungjun@gmail.com"))true else false
                _likeCnt.value=it.like_count

            }.onFailure {
                Log.e(TAG, "unknown error ${it.message}")
            }
        }
    } //하트추가


}