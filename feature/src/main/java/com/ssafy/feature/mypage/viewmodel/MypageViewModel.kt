package com.ssafy.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.mypage.model.MileageStatus
import com.ssafy.domain.mypage.usecase.GetMileageStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val getMileageStatusUseCase: GetMileageStatusUseCase
) : ViewModel() {

    private val _mileageStatus = MutableStateFlow<MileageStatus?>(null)
    val mileageStatus: StateFlow<MileageStatus?> = _mileageStatus

    fun loadMileageStatus() {
        viewModelScope.launch {
            val result = getMileageStatusUseCase()
            result.onSuccess {
                _mileageStatus.value = it
            }.onFailure {
                // 로그 처리나 오류 상태 처리 가능
            }
        }
    }
}
