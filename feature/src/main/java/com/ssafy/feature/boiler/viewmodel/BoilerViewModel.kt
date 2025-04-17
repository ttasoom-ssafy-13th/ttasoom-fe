package com.ssafy.feature.boiler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.bolier.model.Boiler
import com.ssafy.domain.bolier.usecase.GetBoilerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoilerViewModel @Inject constructor(
    private val getBoilerListUseCase: GetBoilerListUseCase
) : ViewModel() {

    private val _boilerList = MutableLiveData<List<Boiler>>()
    val boilerList: LiveData<List<Boiler>> = _boilerList

    fun loadAllBoilers() {
        viewModelScope.launch {
            val result = getBoilerListUseCase.execute()
            _boilerList.value = result
        }
    }

    fun loadFilteredBoilers(
        company: String,
        certification: String,
        circulationType: String,
        fuel: String
    ) {
        viewModelScope.launch {
            val result = getBoilerListUseCase.filter(company, certification, circulationType, fuel)
            _boilerList.value = result
        }
    }
}
