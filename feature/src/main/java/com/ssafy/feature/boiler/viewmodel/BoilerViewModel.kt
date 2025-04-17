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

    fun fetchBoilers() {
        viewModelScope.launch {
            val result = getBoilerListUseCase()
            _boilerListResult.value = result
        }
    }

    fun fetchFilteredBoilers(
        companyName: String? = null,
        certificationType: String? = null,
        circulationType: String? = null,
        fuelType: String? = null
    ) {
        viewModelScope.launch {
            val result = getFilterBoilerListUseCase(
                companyName, certificationType, circulationType, fuelType
            )
            _boilerListResult.value = result
        }
    }
}

