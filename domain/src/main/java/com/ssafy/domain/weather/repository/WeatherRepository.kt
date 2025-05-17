package com.ssafy.domain.weather.repository

import com.ssafy.domain.weather.model.Advice
import com.ssafy.domain.weather.model.Weather

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<Weather>
    suspend fun getAdvice(lat: Double, lon: Double): Result<Advice>
}