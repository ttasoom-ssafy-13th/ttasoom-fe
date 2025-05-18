package com.ssafy.domain.weather.usecase

import com.ssafy.domain.weather.model.Weather
import com.ssafy.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<Weather> {
        return repository.getWeather(lat, lon)
    }
}
