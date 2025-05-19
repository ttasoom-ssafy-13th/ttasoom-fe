package com.ssafy.feature.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.usecase.community.GetBoardUseCase
import com.ssafy.domain.community.usecase.community.PostBoardUseCase
import com.ssafy.domain.community.usecase.community.PutBoardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "EditViewModel_싸피"

@HiltViewModel
class EditViewModel @Inject constructor(
    private val postBoardUseCase: PostBoardUseCase,
    private val putBoardUseCase: PutBoardByIdUseCase,
    private val getBoardUseCase: GetBoardUseCase,
) : ViewModel() {

//    private val _boardList = MutableStateFlow(mutableListOf<Board>())
//    val boardList: StateFlow<MutableList<Board>> get() = _boardList

    private val _board = MutableStateFlow(Board())
    val board: StateFlow<Board> get() = _board

    private val _isUpdate = MutableStateFlow(false)
    val isUpdate : StateFlow<Boolean> = _isUpdate

    fun postBoard(title: String, content: String) {
        viewModelScope.launch {
            postBoardUseCase(title, content)
                .onSuccess {
                    _isUpdate.value=true
                    _isUpdate.value=false
                }
                .onFailure {
                    Log.e("error", "unknown error ${it.message}")
                }

        }
    }

    fun putBoard(post_id: String, title: String, content: String) {
        viewModelScope.launch {

            putBoardUseCase(post_id, title, content)
                .onSuccess {
                    _board.value=Board()
                    _board.value=it

                    _isUpdate.value=true
                }
                .onFailure {
                    Log.e("error", "unknown error ${it.message}")
                }

        }

    }

}