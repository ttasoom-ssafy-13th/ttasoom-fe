package com.ssafy.feature.boiler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.usecase.GetBoilerListUseCase
import com.ssafy.domain.boiler.usecase.GetFilterBoilerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilerViewModel @Inject constructor(
    private val getBoilerListUseCase: GetBoilerListUseCase,
    private val getFilterBoilerListUseCase: GetFilterBoilerListUseCase
) : ViewModel() {

    private val _boilerListResult = MutableLiveData<Result<List<Boiler>>>()
    val boilerListResult: LiveData<Result<List<Boiler>>> = _boilerListResult

    // ✅ 캐싱용
    private var cachedBoilers: List<Boiler> = emptyList()

    fun fetchFilteredBoilers(
        companyNames: List<String>? = null,
        certificationTypes: List<String>? = null,
        circulationTypes: List<String>? = null,
        fuelTypes: List<String>? = null
    ) {
        viewModelScope.launch {
            val result = getFilterBoilerListUseCase(
                companyNames, certificationTypes, circulationTypes, fuelTypes
            )
            result.fold(
                onSuccess = { list ->
                    cachedBoilers = list
                    _boilerListResult.value = Result.success(list)
                },
                onFailure = { e ->
                    _boilerListResult.value = Result.failure(e)
                }
            )
        }
    }

    // ✅ 정렬된 리스트 반환
    fun getSortedBoilers(ascending: Boolean): List<Boiler> {
        return if (ascending) {
            cachedBoilers.sortedBy { it.companyName }
        } else {
            cachedBoilers.sortedByDescending { it.companyName }
        }
    }
}
