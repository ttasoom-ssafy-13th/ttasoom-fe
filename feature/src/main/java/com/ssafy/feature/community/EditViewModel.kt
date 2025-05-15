package com.ssafy.feature.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.usecase.community.GetBoardUseCase
import com.ssafy.domain.community.usecase.community.PostBoardUseCase
import com.ssafy.domain.community.usecase.community.PutBoardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EditViewModel_싸피"

@HiltViewModel
class EditViewModel @Inject constructor(
    private val postBoardUseCase: PostBoardUseCase,
    private val putBoardUseCase: PutBoardByIdUseCase,
) : ViewModel() {

    private val _boardList = MutableStateFlow(mutableListOf<Board>())
    val boardList: StateFlow<MutableList<Board>> get() = _boardList

    private val _board = MutableStateFlow(Board())
    val board: StateFlow<Board> get() = _board

    fun postBoard(title: String, content: String) {
        viewModelScope.launch {

            postBoardUseCase(title, content)
                .onSuccess {
                    _board.value = it

//                    getBoardUseCase().onSuccess {
//                        _boardList.value = ArrayList(it)
//                    }
//                        .onFailure {
//                            Log.e("error", "unknown error ${it.message}")
//                        }
                }
                .onFailure { Log.e("error", "unknown error ${it.message}") }
        }
    }

    fun putBoard(post_id: String, title: String, content: String) {
        viewModelScope.launch {

            putBoardUseCase(post_id, title, content)
                .onSuccess {
                    _board.value = it

//                    getBoardUseCase().onSuccess {
//                        _boardList.value = ArrayList(it)
//                    }
//                        .onFailure {
//                            Log.e("error", "unknown error ${it.message}")
//                        }
                }
                .onFailure {
                    Log.e("error", "unknown error ${it.message}")
                }
        }
    }

}