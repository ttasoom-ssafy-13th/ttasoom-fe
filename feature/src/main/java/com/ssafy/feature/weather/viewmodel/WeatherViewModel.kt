package com.ssafy.feature.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.weather.model.Weather
import com.ssafy.domain.weather.usecase.GetAdviceUseCase
import com.ssafy.domain.weather.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getAdviceUseCase: GetAdviceUseCase
) : ViewModel() {

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    private val _advice = MutableStateFlow<String?>(null)
    val advice: StateFlow<String?> = _advice

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadData(lat: Double, lon: Double) {
        viewModelScope.launch {
            _isLoading.value = true

            // 좌표 로그
            Log.d("WeatherViewModel", "Requesting weather for lat=$lat, lon=$lon")

            val weatherResult = getWeatherUseCase(lat, lon)
            val adviceResult = getAdviceUseCase(lat, lon)

            weatherResult.onSuccess {
                Log.d("WeatherViewModel", "Weather Response: $it")
                _weather.value = it
            }.onFailure {
                Log.e("WeatherViewModel", "Weather Error: ${it.message}")
            }

            adviceResult.onSuccess {
                Log.d("WeatherViewModel", "Advice Response: ${it.content}")
                _advice.value = it.content
            }.onFailure {
                Log.e("WeatherViewModel", "Advice Error: ${it.message}")
            }

            _isLoading.value = false
        }
    }

}
