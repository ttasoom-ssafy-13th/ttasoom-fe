package com.ssafy.data.weather.repository

import com.ssafy.data.weather.mapper.toDomain
import com.ssafy.data.weather.provider.WeatherApi
import com.ssafy.domain.weather.model.Advice
import com.ssafy.domain.weather.model.Weather
import com.ssafy.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Result<Weather> =
        withContext(Dispatchers.IO) {
            runCatching {
                weatherApi.getWeather(lat, lon).weather.toDomain()
            }
        }

    override suspend fun getAdvice(lat: Double, lon: Double): Result<Advice> =
        withContext(Dispatchers.IO) {
            runCatching {
                weatherApi.getAdvice(lat, lon).toDomain()
            }
        }

}
