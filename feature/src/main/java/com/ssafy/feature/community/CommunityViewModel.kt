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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CommunityViewModel_싸피"

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getBoardUseCase: GetBoardUseCase,
) : ViewModel() {

    private val _boardList = MutableStateFlow((mutableListOf<Board>()))
    val boardList: StateFlow<MutableList<Board>> = _boardList.asStateFlow()


    fun getBoard() {
        viewModelScope.launch {

            getBoardUseCase().onSuccess {
                _boardList.value=it.toMutableList()

            }.onFailure {
                Log.e("error", "unknown error ${it.message}")
            }

        }
    }


}