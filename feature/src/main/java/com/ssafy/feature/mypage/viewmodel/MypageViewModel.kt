package com.ssafy.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.mypage.model.MileageHistory
import com.ssafy.domain.mypage.model.MileageStatus
import com.ssafy.domain.mypage.usecase.CheckAttendanceUseCase
import com.ssafy.domain.mypage.usecase.GetMileageHistoryUseCase
import com.ssafy.domain.mypage.usecase.GetMileageStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val getMileageStatusUseCase: GetMileageStatusUseCase,
    private val getMileageHistoryUseCase: GetMileageHistoryUseCase,
    private val checkAttendanceUseCase: CheckAttendanceUseCase
) : ViewModel() {

    private val _mileageStatus = MutableStateFlow<MileageStatus?>(null)
    val mileageStatus: StateFlow<MileageStatus?> = _mileageStatus

    private val _mileageHistory = MutableStateFlow<List<MileageHistory>>(emptyList())
    val mileageHistory: StateFlow<List<MileageHistory>> = _mileageHistory

    fun loadMileageStatus() {
        viewModelScope.launch {
            val result = getMileageStatusUseCase()
            Log.d("MypageViewModel", "loadMileageStatus result: $result")
            result.onSuccess { _mileageStatus.value = it}
        }
    }

    fun loadMileageHistory() {
        viewModelScope.launch {
            val result = getMileageHistoryUseCase()
            result.onSuccess { list ->
                _mileageHistory.value = list.toList() // ✅ 새 객체 강제 할당
            }
        }
    }


    fun checkAttendance(boilerValue: Int) {
        viewModelScope.launch {
            val result = checkAttendanceUseCase(boilerValue).also { Log.d("TAG", "checkAttendance: $it") }
            result.onSuccess {
                delay(300) // ✅ 서버 처리 시간 고려
                loadMileageHistory()
                loadMileageStatus()
            }
        }
    }

}