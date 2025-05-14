package com.ssafy.feature.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.usecase.community.GetBoardUseCase
import com.ssafy.domain.community.usecase.community.PostBoardLikeUseCase
import com.ssafy.domain.community.usecase.community.PostBoardUseCase
import com.ssafy.domain.community.usecase.community.PutBoardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getBoardUseCase: GetBoardUseCase,
    private val postBoardLikeUseCase: PostBoardLikeUseCase
) : ViewModel() {

    private val _boardList = MutableLiveData<MutableList<Board>>()
    val boardList: LiveData<MutableList<Board>> get() = _boardList

    fun getBoard() {
        viewModelScope.launch {
            getBoardUseCase().onSuccess {
                _boardList.value = ArrayList(it)
            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }
        }
    }

    fun


}