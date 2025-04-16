package com.ssafy.feature.boiler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.domain.boiler.usecase.GetBoilerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilerViewModel @Inject constructor(
    private val getBoilerListUseCase: GetBoilerListUseCase
) : ViewModel() {

    private val _boilerListResult = MutableLiveData<Result<List<Boiler>>>()
    val boilerListResult: LiveData<Result<List<Boiler>>> = _boilerListResult

    fun fetchBoilers() {
        viewModelScope.launch {
            val result = getBoilerListUseCase()
            _boilerListResult.value = result
        }
    }
}
