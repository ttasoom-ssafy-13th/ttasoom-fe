package com.ssafy.feature.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.domain.community.usecase.comment.GetCommentUseCase
import com.ssafy.domain.community.usecase.community.DeleteBoardByIdUseCase
import com.ssafy.domain.community.usecase.community.GetBoardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

// 이 viewModel은 주로 게시글 확인할 때 및 댓글 관리 할 때 사용한다.
@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardByIdUseCase: GetBoardByIdUseCase,
    private val deleteBoardByIdUseCase: DeleteBoardByIdUseCase,
    private val getCommentUseCase : GetCommentUseCase
) : ViewModel() {

    var post_id: String = ""
        get() = field
        set(value) {
            field = value
        } //게시글 아이디. communityFragment에서 이미 set해놓음

    private lateinit var board: Board
    private lateinit var commentList: MutableList<Comment>


    fun getBoardById() {
        viewModelScope.launch {
            getBoardByIdUseCase(post_id).onSuccess {
                board = it
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    }

    fun getCommentsByPostId() {
        viewModelScope.launch {
            getCommentUseCase(post_id).onSuccess {
                commentList=ArrayList(it)
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    }

    fun getBoard() : Board =board
    fun getComments(): MutableList<Comment> = commentList

}