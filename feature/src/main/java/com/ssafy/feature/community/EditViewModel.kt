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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val postBoardUseCase: PostBoardUseCase,
    private val putBoardUseCase: PutBoardByIdUseCase,
    private val getBoardUseCase: GetBoardUseCase
) : ViewModel(){

    private val _boardList = MutableLiveData<MutableList<Board>>()
    val boardList: LiveData<MutableList<Board>> get() = _boardList

    private val _board = MutableLiveData<Board>()
    val board: LiveData<Board> get() = _board

    fun postBoard(title: String, content: String) {
        viewModelScope.launch {

            postBoardUseCase(title, content)
                .onSuccess {
                    _board.value = it

                    getBoardUseCase().onSuccess {
                        _boardList.value = ArrayList(it)
                    }
                        .onFailure {
                            Log.e("error", "unknown error ${it.message}")
                        }
                }
                .onFailure { Log.e("error", "unknown error ${it.message}") }
        }
    }

    fun putBoard(post_id : String, title : String, content: String){
        viewModelScope.launch {

            putBoardUseCase(post_id,title,content)
                .onSuccess {
                    _board.value = it

                    getBoardUseCase().onSuccess {
                        _boardList.value = ArrayList(it)
                    }
                        .onFailure {
                            Log.e("error", "unknown error ${it.message}")
                        }
                }
                .onFailure { Log.e("error", "unknown error ${it.message}") }
        }
    }

}