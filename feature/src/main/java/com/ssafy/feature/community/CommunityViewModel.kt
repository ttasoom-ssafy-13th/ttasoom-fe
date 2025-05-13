package com.ssafy.feature.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.usecase.community.GetBoardUseCase
import com.ssafy.domain.community.usecase.community.PostBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// 이 viewModel은 viewList하고 글 작성 할 때 사용되는 viewModel
@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getBoardUseCase: GetBoardUseCase,
    private val postBoardUseCase: PostBoardUseCase
) : ViewModel() {

    private val _boardList = MutableLiveData<MutableList<Board>>()
    val boardList : LiveData<MutableList<Board>> get() = _boardList

    private val _board = MutableLiveData<Board>()
    val board : LiveData<Board> get() = _board

    fun getBoard() {
        viewModelScope.launch {
            getBoardUseCase().onSuccess {
                _boardList.value = ArrayList(it)
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    }

    fun postBoard(title: String, content: String) {
        viewModelScope.launch {
            postBoardUseCase(title, content)
                .onSuccess { _board.value = it }
                .onFailure{ Log.e("error", "unknown error ${it.message}") }
        }
    }

}