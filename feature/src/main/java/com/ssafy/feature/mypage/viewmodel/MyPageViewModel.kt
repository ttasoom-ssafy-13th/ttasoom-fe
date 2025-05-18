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
class MyPageViewModel @Inject constructor(
    private val getMileageStatusUseCase: GetMileageStatusUseCase,
    private val getMileageHistoryUseCase: GetMileageHistoryUseCase,
    private val checkAttendanceUseCase: CheckAttendanceUseCase
) : ViewModel() {

    private val _mileageStatus = MutableStateFlow<MileageStatus?>(null)
    val mileageStatus: StateFlow<MileageStatus?> = _mileageStatus

    private val _mileageHistory = MutableStateFlow<List<MileageHistory>>(emptyList())
    val mileageHistory: StateFlow<List<MileageHistory>> = _mileageHistory

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadMileageStatus() {
        viewModelScope.launch {
            val result = getMileageStatusUseCase()
            result.onSuccess {
                _mileageStatus.value = it
            }
            result.onFailure { e ->
                val message = when {
                    e.message?.contains("403") == true || e.message?.contains("404") == true ->
                        "아직 마일리지 정보가 없습니다."
                    else ->
                        "마일리지 상태를 불러올 수 없습니다.\n(${e.message})"
                }
                _errorMessage.value = message
                Log.e("MypageViewModel", "❌ Failed to load mileage status", e)
            }
        }
    }



    fun loadMileageHistory() {
        viewModelScope.launch {
            val result = getMileageHistoryUseCase()
            result.onSuccess {
                _mileageHistory.value = it
            }
            result.onFailure { e ->
                val message = when {
                    e.message?.contains("403") == true || e.message?.contains("404") == true ->
                        "마일리지 내역이 아직 없습니다."
                    else ->
                        "마일리지 내역을 불러올 수 없습니다.\n(${e.message})"
                }
                _errorMessage.value = message
                Log.e("MypageViewModel", "❌ Failed to load mileage history", e)
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

    fun calculateAttendanceStreak(attendanceHistory: List<MileageHistory>): Int {
        if (attendanceHistory.isEmpty()) return 0

        val dates = attendanceHistory
            .map { it.createdAt.toLocalDate() }
            .distinct()
            .sortedDescending()

        var streak = 1
        var prevDate = dates[0]

        for (i in 1 until dates.size) {
            val expected = prevDate.minusDays(1)
            if (dates[i] == expected) {
                streak++
                prevDate = dates[i]
            } else if (dates[i].isBefore(expected)) {
                break // 연속이 끊긴 경우
            }
        }

        return streak
    }
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

}